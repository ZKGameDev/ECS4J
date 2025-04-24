package priv.kgame.lib.ecs.system.base;

import priv.kgame.lib.ecs.EcsWorld;
import priv.kgame.lib.ecs.component.ComponentType;
import priv.kgame.lib.ecs.component.EcsComponent;
import priv.kgame.lib.ecs.entity.Entity;
import priv.kgame.lib.ecs.entity.EntityGroup;
import priv.kgame.lib.ecs.system.EcsSystem;
import priv.kgame.lib.ecs.tools.EcsTools;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public abstract class EcsInitializeCustomSystem<T extends EcsComponent, C extends EcsComponent> extends EcsSystem {
    final private Class<T> entityClass;
    final private Class<C> systemState;
    EntityGroup group;
    private final List<ComponentType<?>> extraRequirementComponent = new ArrayList<>();
    @SuppressWarnings("unchecked")
    public EcsInitializeCustomSystem(EcsWorld ecsWorld) {
        super();
        Type[] parameterizedTypes = EcsTools.generateParameterizedType(this.getClass());
        entityClass = (Class<T>) parameterizedTypes[0];
        systemState = (Class<C>) parameterizedTypes[1];
//        init(ecsWorld);
    }

    @Override
    protected void onInit() {
        List<ComponentType<?>> typeList = new ArrayList<>();
        typeList.add(ComponentType.create(entityClass));
        if (!extraRequirementComponent.isEmpty()) {
            typeList.addAll(extraRequirementComponent);
        }
        typeList.add(ComponentType.subtractive(systemState));
        group = getOrAddEntityGroup(typeList);
        setAlwaysUpdateSystem(true);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void onUpdate() {
        List<Entity> entityList = group.getEntityList();
        for (Entity entity : entityList) {
            ComponentType<T> componentType = ComponentType.create(entityClass);
            entity.assertContainComponent(componentType);
            if (onInitialize(entity, (T)(entity.getData().get(componentType)))) {
                try {
                    getWorld().addComponent(entity, systemState.getDeclaredConstructor().newInstance());
                } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                         NoSuchMethodException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    protected void addExtraRequireComponent(Class<? extends EcsComponent> klass) {
        extraRequirementComponent.add(ComponentType.create(klass));
    }

    public abstract boolean onInitialize(Entity entity, T data);
}
