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
import java.util.Collection;
import java.util.List;

//todo 存在的意义
public abstract class EcsInitializeSystem<T extends EcsComponent> extends EcsSystem {
    public static abstract class SystemInitFinishSingle implements EcsComponent {}
    final private Class<T> entityClass;
    EntityGroup group;
    private final List<ComponentType<?>> extraRequirementComponent = new ArrayList<>();
    private final SystemInitFinishSingle systemInitFinishSingle;

    @SuppressWarnings("unchecked")
    public EcsInitializeSystem(EcsWorld ecsWorld) {
        super(ecsWorld);
        Type[] parameterizedTypes = EcsTools.generateParameterizedType(this.getClass());
        entityClass = (Class<T>) parameterizedTypes[0];
        systemInitFinishSingle = getInitFinishSingle();
    }

    @Override
    protected void onInit() {
        List<ComponentType<?>> typeList = new ArrayList<>();
        typeList.add(ComponentType.additive(getWorld(), entityClass));
        processExtraComponent();
        if (!extraRequirementComponent.isEmpty()) {
            typeList.addAll(extraRequirementComponent);
        }
        typeList.add(ComponentType.subtractive(getWorld(), systemInitFinishSingle.getClass()));
        group = getOrAddEntityGroup(typeList);
        setAlwaysUpdateSystem(true);
    }

    private void processExtraComponent() {
        Collection<Class<? extends EcsComponent>> requireComponent = getExtraRequirementComponent();
        if (requireComponent != null && !requireComponent.isEmpty()) {
            for (Class<? extends EcsComponent> clazz : requireComponent) {
                extraRequirementComponent.add(ComponentType.additive(getWorld(), clazz));
            }
        }
        Collection<Class<? extends EcsComponent>> excludeComponent = getExtraRequirementComponent();
        if (excludeComponent != null && !excludeComponent.isEmpty()) {
            for (Class<? extends EcsComponent> clazz : excludeComponent) {
                extraRequirementComponent.add(ComponentType.subtractive(getWorld(), clazz));
            }
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void onUpdate() {
        List<Entity> entityList = group.getEntityList();
        for (Entity entity : entityList) {
            ComponentType<T> componentType = ComponentType.additive(getWorld(), entityClass);
            entity.assertContainComponent(componentType);
            if (onInitialize(entity, (T)(entity.getData().get(componentType)))) {
                getWorld().addComponent(entity, systemInitFinishSingle);
            }
        }
    }

    protected void addExtraRequireComponent(Class<? extends EcsComponent> klass) {
        extraRequirementComponent.add(ComponentType.additive(getWorld(), klass));
    }

    public abstract boolean onInitialize(Entity entity, T data);

    /**
     * 额外需要关注的Component类
     * @return 关注的Component类的集合
     */
    public abstract Collection<Class<? extends EcsComponent>> getExtraRequirementComponent();

    /**
     * 额外需要排除的Component类
     * @return 要排除的Component类的集合
     */
    public abstract Collection<Class<? extends EcsComponent>> getExtraExcludeComponent();
    public abstract SystemInitFinishSingle getInitFinishSingle();

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
