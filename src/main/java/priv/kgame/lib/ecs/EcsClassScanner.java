package priv.kgame.lib.ecs;

import priv.kgame.lib.ecs.component.EcsComponent;
import priv.kgame.lib.ecs.entity.EntityFactory;
import priv.kgame.lib.ecs.entity.EntityFactoryAttribute;
import priv.kgame.lib.ecs.exception.InvalidEcsEntityFactoryException;
import priv.kgame.lib.ecs.exception.InvalidEcsTypeException;
import priv.kgame.lib.ecs.exception.InvalidEcsTypeIndexException;
import priv.kgame.lib.ecs.system.EcsSystem;
import priv.kgame.lib.ecs.system.EcsSystemGroup;
import priv.kgame.lib.ecs.system.annotation.UpdateInGroup;
import priv.kgame.lib.ecs.tools.ClassUtils;

import java.lang.annotation.Annotation;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class EcsClassScanner {
    private final Map<Class<? extends EcsSystemGroup>, Set<Class<? extends EcsSystem>>> groupChildTypeMap = new HashMap<>();
    private final Set<Class<? extends EntityFactory>> entityFactoryClass = new HashSet<>();
    private final HashMap<Integer, Class<?>> indexComponentTypeMap = new HashMap<>();
    private final HashMap<Class<?>, Integer> componentTypeIndexMap = new HashMap<>();
    private int componentTypeIndex = 0;

    private static final Map<String, EcsClassScanner> SCANNERS = new ConcurrentHashMap<>();
    public static EcsClassScanner getInstance(String packageName) {
        return SCANNERS.computeIfAbsent(packageName, name -> {
            EcsClassScanner newInstance = new EcsClassScanner();
            newInstance.loadPackage(EcsClassScanner.class.getPackageName());
            newInstance.loadPackage(packageName);
            return newInstance;
        });
    }

    public Set<Class<? extends EntityFactory>> getEntityFactoryClass() {
        return entityFactoryClass;
    }

    private void loadPackage(String scanPackage) {
        Set<Class<? extends EcsSystem>> updateInGroupClass = getEcsSystemByAnnotation(scanPackage, UpdateInGroup.class);
        for (Class<? extends EcsSystem> clazz : updateInGroupClass) {
            UpdateInGroup updateInGroupAnnotation = clazz.getAnnotation(UpdateInGroup.class);
            if (updateInGroupAnnotation == null) {
                continue;
            }
            Class<? extends EcsSystemGroup> groupClass = updateInGroupAnnotation.value();
            if (ClassUtils.isAbstract(groupClass)
                    || !EcsSystemGroup.class.isAssignableFrom(groupClass)) {
                continue;
            }
            groupChildTypeMap.computeIfAbsent(groupClass, item -> new HashSet<>()).add(clazz);
        }

        entityFactoryClass.addAll(getEcsEntityFactoryByAnnotation(scanPackage));

        registerComponent(EcsWorld.class.getPackage().toString());
        registerComponent(scanPackage);
    }

    private void registerComponent(String scanPackage) {
        for (Class<?> clazz : ClassUtils.getClassFromParent(scanPackage, EcsComponent.class)) {
            if (ClassUtils.isAbstract(clazz)) {
                continue;
            }
            registerComponentType(clazz);
        }
    }

    @SuppressWarnings("unchecked")
    public Set<Class<? extends EcsSystem>> getEcsSystemByAnnotation(String scanPackage, Class<? extends Annotation> annoClass) {
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
    public Set<Class<? extends EntityFactory>> getEcsEntityFactoryByAnnotation(String scanPackage) {
        Set<Class<?>> classes = ClassUtils.getClassByAnnotation(scanPackage, EntityFactoryAttribute.class);
        Set<Class<? extends EntityFactory>> ecsSystemClass = new HashSet<>();
        for (Class<?> klass : classes) {
            if (!ClassUtils.isAbstract(klass) && EntityFactory.class.isAssignableFrom(klass)) {
                ecsSystemClass.add((Class<? extends EntityFactory>) klass);
            } else {
                throw new InvalidEcsEntityFactoryException("class " + klass.getName() + " is not an EntityFactory but is annotated with @EntityFactoryAttribute");
            }
        }
        return ecsSystemClass;
    }

    public Set<Class<? extends EcsSystem>> getChildSystem(Class<? extends EcsSystemGroup> aClass) {
        return groupChildTypeMap.getOrDefault(aClass, Collections.emptySet());
    }

    private void registerComponentType(Class<?> type) {
        indexComponentTypeMap.put(componentTypeIndex, type);
        componentTypeIndexMap.put(type, componentTypeIndex);
        componentTypeIndex++;
    }

    public int getComponentTypeIndex(Class<?> type) {
        Integer index = componentTypeIndexMap.get(type);
        if (null == index) {
            throw new InvalidEcsTypeException(type);
        }
        return index;
    }

    public Class<?> getComponentType(int index) {
        Class<?> type = indexComponentTypeMap.get(index);
        if (null == type) {
            throw new InvalidEcsTypeIndexException(index);
        }
        return type;
    }

}
