package priv.kgame.lib.ecs.system.base;

import priv.kgame.lib.ecs.EcsWorld;
import priv.kgame.lib.ecs.component.ComponentType;
import priv.kgame.lib.ecs.component.EcsComponent;
import priv.kgame.lib.ecs.entity.Entity;
import priv.kgame.lib.ecs.entity.EntityGroup;
import priv.kgame.lib.ecs.system.EcsSystem;
import priv.kgame.lib.ecs.tools.EcsTools;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

//todo 存在的意义
public abstract class EcsInitializeSystem<T extends EcsComponent> extends EcsSystem {
    public static class SystemState implements EcsComponent {}
    final private Class<T> entityClass;
    EntityGroup group;
    private final List<ComponentType<?>> extraRequirementComponent = new ArrayList<>();
    @SuppressWarnings("unchecked")
    public EcsInitializeSystem(EcsWorld ecsWorld) {
        super(ecsWorld);
        Type[] parameterizedTypes = EcsTools.generateParameterizedType(this.getClass());
        entityClass = (Class<T>) parameterizedTypes[0];
    }

    @Override
    protected void onInit() {
        List<ComponentType<?>> typeList = new ArrayList<>();
        typeList.add(ComponentType.additive(getWorld(), entityClass));
        if (!extraRequirementComponent.isEmpty()) {
            typeList.addAll(extraRequirementComponent);
        }
        typeList.add(ComponentType.subtractive(getWorld(), SystemState.class));
        group = getOrAddEntityGroup(typeList);
        setAlwaysUpdateSystem(true);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void onUpdate() {
        List<Entity> entityList = group.getEntityList();
        for (Entity entity : entityList) {
            ComponentType<T> componentType = ComponentType.additive(getWorld(), entityClass);
            entity.assertContainComponent(componentType);
            if (onInitialize(entity, (T)(entity.getData().get(componentType)))) {
                getWorld().addComponent(entity, new SystemState());
            }
        }
    }

    protected void addExtraRequireComponent(Class<? extends EcsComponent> klass) {
        extraRequirementComponent.add(ComponentType.additive(getWorld(), klass));
    }

    public abstract boolean onInitialize(Entity entity, T data);

    @Override
    protected void onStart() {

    }

    @Override
    protected void onStop() {

    }

    @Override
    protected void onDestroy() {

    }
}
