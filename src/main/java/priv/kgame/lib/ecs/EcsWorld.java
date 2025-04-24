package priv.kgame.lib.ecs;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import priv.kgame.lib.ecs.tools.ClassUtils;
import priv.kgame.lib.ecs.component.ComponentAccessMode;
import priv.kgame.lib.ecs.component.ComponentType;
import priv.kgame.lib.ecs.component.ComponentTypeQuery;
import priv.kgame.lib.ecs.component.EcsComponent;
import priv.kgame.lib.ecs.entity.*;
import priv.kgame.lib.ecs.system.EcsSystem;
import priv.kgame.lib.ecs.system.EcsSystemGroup;
import priv.kgame.lib.ecs.system.annotation.AutoCreate;
import priv.kgame.lib.ecs.system.annotation.UpdateInGroup;
import priv.kgame.lib.ecs.system.annotation.UpdateIntervalTime;
import priv.kgame.lib.ecs.tools.TypeUtility;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class EcsWorld implements Disposable {
    private static final Logger logger = LogManager.getLogger(EcsWorld.class);

    private long currentTime;
    private final EntityArchetype emptyArchetype = new EntityArchetype();
    private final List<EntityArchetype> entityArchetypes = new ArrayList<>();

    List<EntityGroup> systemNeedEntityGroups = new ArrayList<>();

    private int entitiesNextIndex = 1;
    Map<Integer, Entity> entityIndex = new HashMap<>();


    private int systemNextIndex = 1;
    Map<Class<?>, EcsSystem> systemClassIndex = new HashMap<>();
    Set<EcsSystem> systems = new TreeSet<>(Comparator.comparingInt(EcsSystem::getSystemCreateOrder));

    List<EcsSystemGroup> systemGroups = new ArrayList<>();

    @Override
    public void dispose() {
        currentTime = 0;
        clearSystems();
        clearEntityGroups();
        clearEntity();
        clearEntityArchetypes();
    }

    private void clearEntityArchetypes() {
        entityArchetypes.forEach(EntityArchetype::dispose);
        entityArchetypes.clear();
    }

    private void clearEntityGroups() {
        systemNeedEntityGroups.forEach(EntityGroup::dispose);
        systemNeedEntityGroups.clear();
    }

    private void clearEntity() {
        entitiesNextIndex = 1;
        entityIndex.values().forEach(Entity::dispose);
        entityIndex.clear();
    }

    private void clearSystems() {
        systemNextIndex = 1;
        systems.forEach(EcsSystem::dispose);
        systems.clear();
        systemClassIndex.clear();
    }

    public Entity createEntity(int type) {
        return createEntity(type, Collections.emptyList());
    }

    public Entity createEntity(int typeId, Collection<ComponentType<?>> types) {
        return createEntity(typeId, getOrCreateArchetype(types));
    }

    public Entity createEntity(int typeId, EntityArchetype entityArchetype) {
        Entity entity = new Entity(entitiesNextIndex++, typeId, entityArchetype);
        for (ComponentType<?> componentType : entityArchetype.getComponentTypes()) {
            entity.addComponent(componentType);
        }
        entityArchetype.addEntity(entity);
        entityIndex.put(entity.getIndex(), entity);
        return entity;
    }

    private EntityArchetype getOrCreateArchetype(Collection<ComponentType<?>> types) {
        EntityArchetype existArchetype = getExistingArchetype(types);
        if (existArchetype != null) {
            return existArchetype;
        }
        return createArchetype(types);
    }

    private EntityArchetype getExistingArchetype(Collection<ComponentType<?>> types) {
        if (null == types || types.isEmpty()) {
            return emptyArchetype;
        } else {
            for (EntityArchetype entityArchetype : entityArchetypes) {
                if (entityArchetype.isSame(types)) {
                    return entityArchetype;
                }
            }
            return null;
        }
    }

    private EntityArchetype createArchetype(Collection<ComponentType<?>> types) {
        EntityArchetype entityArchetype = new EntityArchetype();
        for (ComponentType<?> componentType : types) {
            if (componentType.getAccessModeType() != ComponentAccessMode.SUBTRACTIVE) {
                entityArchetype.addComponentType(componentType);
            }
        }
        entityArchetypes.add(entityArchetype);
        systemNeedEntityGroups.forEach(entityGroup -> addArchetypeIfMatching(entityGroup, entityArchetype));
        return entityArchetype;
    }

    private void addArchetypeIfMatching(EntityGroup entityGroup, EntityArchetype entityArchetype) {
        if (isMatchingArchetype(entityArchetype, entityGroup)) {
            entityGroup.addMatchType(entityArchetype);
        }
    }

    private boolean isMatchingArchetype(EntityArchetype entityArchetype, EntityGroup entityGroup) {
        for (ComponentTypeQuery query : entityGroup.getRequirementQuery()) {
            if (isMatchingArchetype(entityArchetype, query)) {
                return true;
            }
        }
        return false;
    }

    private boolean isMatchingArchetype(EntityArchetype entityArchetype, ComponentTypeQuery query) {
        if (!checkMatchingArchetypeAll(entityArchetype, query.getAll())) {
            return false;
        }
        if (!checkMatchingArchetypeNone(entityArchetype, query.getNone())) {
            return false;
        }
        return checkMatchingArchetypeAny(entityArchetype, query.getAny());
    }

    private boolean checkMatchingArchetypeAll(EntityArchetype entityArchetype, TreeSet<ComponentType<?>> all) {
        for (ComponentType<?> componentType : all) {
            if (!entityArchetype.getComponentTypes().contains(componentType)) {
                return false;
            }
        }
        return true;
    }

    private boolean checkMatchingArchetypeNone(EntityArchetype entityArchetype, TreeSet<ComponentType<?>> none) {
        for (ComponentType<?> componentType : entityArchetype.getComponentTypes()) {
            if (none.contains(componentType)) {
                return false;
            }
        }
        return true;
    }

    private boolean checkMatchingArchetypeAny(EntityArchetype entityArchetype, TreeSet<ComponentType<?>> any) {
        if (any.isEmpty()) {
            return true;
        }
        for (ComponentType<?> componentType : entityArchetype.getComponentTypes()) {
            if (any.contains(componentType)) {
                return true;
            }
        }
        return false;
    }


    public void destroyEntity(Entity entity) {
        if (notExistEntity(entity)) {
            logger.warn("destroy entity failed! reason: entity not exist. index:{}", entity.getIndex());
            return;
        }
        boolean success = entity.removeFromArchetype();
        if (entityIndex.remove(entity.getIndex()) != null) {
            success = true;
        }
        if (success) {
            entity.dispose();
        }
    }

    private boolean notExistEntity(Entity entity) {
        if (entitiesNextIndex < entity.getIndex()) {
            return true;
        }
        return !entityIndex.containsKey(entity.getIndex());
    }

    public void addComponent(Entity entity, EcsComponent component) {
        if (notExistEntity(entity)) {
            logger.warn("add component failed! reason: entity not exist. index:{}", entity.getIndex());
            return;
        }
        ComponentType<?> componentType = ComponentType.create(component.getClass());
        if (entity.hasComponent(componentType)) {
            logger.warn("add component failed! reason: component already exists of entity:{} componentType:{}",
                    entity.getIndex(), componentType.getType().getSimpleName());
            return;
        }

        EntityArchetype oldArchetype = entity.getArchetype();
        Set<ComponentType<?>> newTypes = new HashSet<>(oldArchetype.getComponentTypes());
        newTypes.add(componentType);
        setArchetype(getOrCreateArchetype(newTypes), oldArchetype, entity);
        entity.addComponent(componentType, component);
    }

    private void setArchetype(EntityArchetype newArchetype, EntityArchetype oldArchetype, Entity entity) {
        newArchetype.addEntity(entity);
        oldArchetype.removeEntity(entity);
        entity.setArchetype(newArchetype);
    }

    public void removeComponent(Entity entity, EcsComponent component) {
        ComponentType<?> componentType = ComponentType.create(component.getClass());
        EntityArchetype oldArchetype = entity.getArchetype();
        Set<ComponentType<?>> newTypes = new HashSet<>(oldArchetype.getComponentTypes());
        newTypes.remove(componentType);
        setArchetype(getOrCreateArchetype(newTypes), oldArchetype, entity);
        entity.removeComponent(componentType);
    }

    public EntityGroup createEntityGroup(ComponentTypeQuery[] componentTypeQuery) {
        EntityGroup entityGroup = null;
        for (EntityGroup item : systemNeedEntityGroups) {
            if (item.compareQuery(componentTypeQuery)) {
                entityGroup = item;
            }
        }
        if (entityGroup == null) {
            EntityGroup newEntityGroup = new EntityGroup();
            newEntityGroup.addRequirementQuery(componentTypeQuery);
            entityArchetypes.forEach(it -> addArchetypeIfMatching(newEntityGroup, it));
            systemNeedEntityGroups.add(newEntityGroup);
            return newEntityGroup;
        } else {
            return entityGroup;
        }
    }

    public ComponentTypeQuery createQuery(ComponentType<?>[] componentTypes) {
        ComponentTypeQuery result = new ComponentTypeQuery();
        for (ComponentType<?> type : componentTypes) {
            if (type.getAccessModeType() == ComponentAccessMode.SUBTRACTIVE) {
                result.addNone(type);
            } else {
                result.addAll(type);
            }
        }
        return result;
    }

    public Set<Class<? extends EcsSystem>> getChildSystemInGroup(EcsSystemGroup ecsSystemGroup) {
        return GROUP_CHILD_TYPE_MAP.get(ecsSystemGroup.getClass());
    }

    @SuppressWarnings("unchecked")
    public <T extends EcsSystem> T createSystem(Class<T> systemClass) {
        EcsSystem system = systemClassIndex.get(systemClass);
        if (null != system) {
            return (T)system;
        }
        try {
            if (EcsSystemGroup.class.isAssignableFrom(systemClass)) {
                system = systemClass.getConstructor().newInstance();
            }else {
                system = systemClass.getConstructor(this.getClass()).newInstance(this);
            }
            system.setSystemCreateOrder(systemNextIndex++);
            UpdateIntervalTime timeIntervalAnno = systemClass.getAnnotation(UpdateIntervalTime.class);
            if (null != timeIntervalAnno) {
                system.setUpdateInterval((long) (timeIntervalAnno.interval() * 1000f));
            }
            Class<? extends EcsSystem> klass= systemClass;
            while (!klass.equals(EcsSystem.class)) {
                if (!systemClassIndex.containsKey(systemClass)) {
                    systemClassIndex.put(systemClass, system);
                }
                if (EcsSystem.class.isAssignableFrom(klass.getSuperclass())) {
                    klass = (Class<? extends EcsSystem>) klass.getSuperclass();
                }
            }
            system.init(this, systemNextIndex++);
            systems.add(system);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
        return (T)system;
    }

    public long getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(long currentTime) {
        this.currentTime = currentTime;
    }

    public void destroySystem(EcsSystem system) {
        if (systemClassIndex.containsKey(system.getClass())) {
            removeSystem(system);
        }
    }

    private void removeSystem(EcsSystem system) {
        if (!systems.remove(system)) {
            return;
        }
        Class<?> klass = system.getClass();
        while (!klass.equals(EcsSystem.class)) {
            systemClassIndex.remove(klass);
            klass = klass.getSuperclass();
        }
    }

    private static final Set<Class<?>> AUTO_CREATE_SYSTEMS = new HashSet<>();
    private static final Map<Class<? extends EcsSystemGroup>, Set<Class<? extends EcsSystem>>> GROUP_CHILD_TYPE_MAP = new HashMap<>();
    private static final Set<Class<? extends EntityFactory>> ENTITY_FACTORY_CLASS = new HashSet<>();

    public static Set<Class<? extends EntityFactory>> getEntityFactoryClass() {
        return ENTITY_FACTORY_CLASS;
    }

    private static boolean initFinished = false;
    private final static String defaultScanPackage = "priv.kgame.game";

    public static void init() {
        init(defaultScanPackage);
    }

    public static void init(String scanPackage) {
        if (initFinished) {
            return;
        }

        AUTO_CREATE_SYSTEMS.addAll(getEcsSystemByAnnotation(scanPackage, AutoCreate.class));

        Set<Class<? extends EcsSystem>> updateInGroupClass = getEcsSystemByAnnotation(scanPackage, UpdateInGroup.class);
        for (Class<? extends EcsSystem> clazz : updateInGroupClass) {
            UpdateInGroup updateInGroupAnnotation = clazz.getAnnotation(UpdateInGroup.class);
            Class<? extends EcsSystemGroup> groupClass = updateInGroupAnnotation.groupType();
            if (ClassUtils.isAbstract(groupClass)
                    || !EcsSystemGroup.class.isAssignableFrom(groupClass)) {
                continue;
            }
            GROUP_CHILD_TYPE_MAP.computeIfAbsent(groupClass, item -> new HashSet<>()).add(clazz);
        }

        ENTITY_FACTORY_CLASS.addAll(getEcsEntityFactoryByAnnotation(scanPackage));

        registerComponent(EcsWorld.class.getPackage().toString());
        registerComponent(scanPackage);

        initFinished = true;
    }

    private static void registerComponent(String scanPackage) {
        for (Class<?> clazz : ClassUtils.getClassFromParent(scanPackage, EcsComponent.class)) {
            if (ClassUtils.isAbstract(clazz)) {
                continue;
            }
            TypeUtility.getInstance().registerType(clazz);
        }
    }

    @SuppressWarnings("unchecked")
    public static Set<Class<? extends EcsSystem>> getEcsSystemByAnnotation(String scanPackage, Class<? extends Annotation> annoClass) {
        Set<Class<?>> classes = ClassUtils.getClassByAnnotation(scanPackage, annoClass);
        Set<Class<? extends EcsSystem>> ecsSystemClass = new HashSet<>();
        for (Class<?> klass : classes) {
            if (!ClassUtils.isAbstract(klass) && EcsSystem.class.isAssignableFrom(klass)) {
                ecsSystemClass.add((Class<? extends EcsSystem>) klass);
            }
        }
        return ecsSystemClass;
    }

    @SuppressWarnings("unchecked")
    public static Set<Class<? extends EntityFactory>> getEcsEntityFactoryByAnnotation(String scanPackage) {
        Set<Class<?>> classes = ClassUtils.getClassByAnnotation(scanPackage, EntityFactoryAttribute.class);
        Set<Class<? extends EntityFactory>> ecsSystemClass = new HashSet<>();
        for (Class<?> klass : classes) {
            if (!ClassUtils.isAbstract(klass) && EntityFactory.class.isAssignableFrom(klass)) {
                ecsSystemClass.add((Class<? extends EntityFactory>) klass);
            }
        }
        return ecsSystemClass;
    }

    public void registerSystemGroup(Class<? extends EcsSystemGroup> clz) {
        EcsSystemGroup systemGroup = createSystem(clz);
        this.systemGroups.add(systemGroup);
    }

    public void tryUpdate(long now) {
        setCurrentTime(now);
        for (EcsSystemGroup systemGroup : this.systemGroups) {
            systemGroup.tryUpdate();
        }
    }

    public <T extends EcsComponent> T getComponent(int index, Class<T> compClass) {
        Entity entity = entityIndex.get(index);
        if (null == entity) {
            return null;
        }
        return entity.getComponent(compClass);
    }

    public Entity getEntityByIndex(int index) {
        return entityIndex.get(index);
    }

    public Collection<Entity> getAllEntity(){
        return entityIndex.values();
    }
}
