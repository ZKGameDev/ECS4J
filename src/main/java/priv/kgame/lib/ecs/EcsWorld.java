package priv.kgame.lib.ecs;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import priv.kgame.lib.ecs.component.ComponentAccessMode;
import priv.kgame.lib.ecs.component.ComponentType;
import priv.kgame.lib.ecs.component.ComponentTypeQuery;
import priv.kgame.lib.ecs.component.EcsComponent;
import priv.kgame.lib.ecs.entity.Entity;
import priv.kgame.lib.ecs.entity.EntityArchetype;
import priv.kgame.lib.ecs.entity.EntityFactory;
import priv.kgame.lib.ecs.entity.EntityGroup;
import priv.kgame.lib.ecs.exception.InvalidEcsEntityFactoryException;
import priv.kgame.lib.ecs.system.EcsSystem;
import priv.kgame.lib.ecs.system.EcsSystemGroup;
import priv.kgame.lib.ecs.system.annotation.UpdateIntervalTime;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class EcsWorld implements Disposable {
    private static final Logger logger = LogManager.getLogger(EcsWorld.class);

    private long currentTime;
    private final EntityArchetype emptyArchetype = new EntityArchetype();
    private final List<EntityArchetype> entityArchetypes = new ArrayList<>();
    private final List<EntityGroup> systemNeedEntityGroups = new ArrayList<>();

    private int entitiesNextIndex = 1;
    private final Map<Integer, Entity> entityIndex = new HashMap<>();

    private int systemNextIndex = 1;
    private final Map<Class<?>, EcsSystem> systemClassIndex = new HashMap<>();
    private final Set<EcsSystem> systems = new TreeSet<>(Comparator.comparingInt(EcsSystem::getSystemCreateOrder));
    private final List<EcsSystemGroup> systemGroups = new ArrayList<>();

    private final EcsClassScanner ecsClassScanner;
    private final EntityFactoryIndex entityFactoryIndex = new EntityFactoryIndex();

    private static class EntityFactoryIndex {
        private final Map<Integer, EntityFactory> entityFactoryTypeIdIndex = new HashMap<>();
        private final Map<Class<? extends EntityFactory>, EntityFactory> entityFactoryClassIndex = new HashMap<>();

        private void registerEntityFactory(EntityFactory entityFactory) {
            if (entityFactory.typeId() == 0) {
                throw new InvalidEcsEntityFactoryException("Invalid Factory, typeId must not be 0 !");
            }

            if (entityFactoryTypeIdIndex.containsKey(entityFactory.typeId())) {
                logger.error("EntityFactory already exist. name:{} typeId:{}", entityFactory.getClass().getName(), entityFactory.typeId());
                throw new InvalidEcsEntityFactoryException("EntityFactory already exist. name:" + entityFactory.getClass().getName() + " typeId:" + entityFactory.typeId());
            }

            entityFactoryTypeIdIndex.put(entityFactory.typeId(), entityFactory);
            entityFactoryClassIndex.put(entityFactory.getClass(), entityFactory);
        }

        private EntityFactory get(int typeId) {
            return entityFactoryTypeIdIndex.get(typeId);
        }
        private EntityFactory get(Class<? extends EntityFactory> clazz) {
            return entityFactoryClassIndex.get(clazz);
        }

        private void clear() {
            entityFactoryTypeIdIndex.clear();
            entityFactoryClassIndex.clear();
        }
    }


    public EcsWorld(String packageName) {
        ecsClassScanner = EcsClassScanner.getInstance(packageName);
        ecsClassScanner.getEntityFactoryClass().forEach(entityFactoryClass -> {
            try {
                EntityFactory entityFactory = entityFactoryClass.getDeclaredConstructor().newInstance();
                entityFactoryIndex.registerEntityFactory(entityFactory);
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                     NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
        });
    }


    public Set<Class<? extends EntityFactory>> getEntityFactoryClass() {
        return ecsClassScanner.getEntityFactoryClass();
    }

    @Override
    public void dispose() {
        currentTime = 0;
        clearSystems();
        clearEntityGroups();
        clearEntity();
        clearEntityArchetypes();
        entityFactoryIndex.clear();
    }

    // 通过类型ID创建实体
    public Entity createEntityByFactory(int typeId) {
        EntityFactory entityFactory = entityFactoryIndex.get(typeId);
        return entityFactory == null ? null : entityFactory.create(this);
    }

    // 通过工厂类创建实体
    public Entity createEntityByFactory(Class<? extends EntityFactory> klass) {
        EntityFactory entityFactory = entityFactoryIndex.get(klass);
        return entityFactory == null ? null : entityFactory.create(this);
    }

    public Entity createEntity(int type) {
        return createEntity(type, Collections.emptyList());
    }

    public Entity createEntity(int typeId, Collection<ComponentType<?>> types) {
        return createEntity(typeId, getOrCreateArchetype(types));
    }

    public Entity createEntity(int typeId, EntityArchetype entityArchetype) {
        Entity entity = new Entity(this, entitiesNextIndex++, typeId, entityArchetype);
        for (ComponentType<?> componentType : entityArchetype.getComponentTypes()) {
            entity.addComponent(componentType);
        }
        entityArchetype.addEntity(entity);
        entityIndex.put(entity.getIndex(), entity);
        return entity;
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

    public Entity getEntityByIndex(int index) {
        return entityIndex.get(index);
    }

    public Collection<Entity> getAllEntity() {
        return entityIndex.values();
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

    public int getComponentTypeIndex(Class<?> type) {
        return ecsClassScanner.getComponentTypeIndex(type);
    }

    public void addComponent(Entity entity, EcsComponent component) {
        if (notExistEntity(entity)) {
            logger.warn("add component failed! reason: entity not exist. index:{}", entity.getIndex());
            return;
        }
        ComponentType<?> componentType = ComponentType.additive(this, component.getClass());
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

    public void removeComponent(Entity entity, EcsComponent component) {
        ComponentType<?> componentType = ComponentType.additive(this, component.getClass());
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
            EntityGroup newEntityGroup = new EntityGroup(this);
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
        return ecsClassScanner.getChildSystem(ecsSystemGroup.getClass());
    }

    @SuppressWarnings("unchecked")
    public <T extends EcsSystem> T createSystem(Class<T> systemClass) {
        EcsSystem system = systemClassIndex.get(systemClass);
        if (null != system) {
            return (T) system;
        }
        try {
            if (EcsSystemGroup.class.isAssignableFrom(systemClass)) {
                system = systemClass.getConstructor().newInstance();
            } else {
                system = systemClass.getConstructor(this.getClass()).newInstance(this);
            }
            system.setSystemCreateOrder(systemNextIndex++);
            UpdateIntervalTime timeIntervalAnno = systemClass.getAnnotation(UpdateIntervalTime.class);
            if (null != timeIntervalAnno) {
                system.setUpdateInterval((long) (timeIntervalAnno.interval() * 1000f));
            }
            Class<? extends EcsSystem> klass = systemClass;
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
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                 NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
        return (T) system;
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

    private boolean notExistEntity(Entity entity) {
        if (entitiesNextIndex < entity.getIndex()) {
            return true;
        }
        return !entityIndex.containsKey(entity.getIndex());
    }

    private void setArchetype(EntityArchetype newArchetype, EntityArchetype oldArchetype, Entity entity) {
        newArchetype.addEntity(entity);
        oldArchetype.removeEntity(entity);
        entity.setArchetype(newArchetype);
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
}
