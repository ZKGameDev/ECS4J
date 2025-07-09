package priv.kgame.lib.ecs.system.base;

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

/**
 * 实体初始化系统基类
 * <p>
 * 该类继承自EcsSystem，用于实现实体的初始化逻辑。
 * 特点：
 * 1. 系统会自动处理包含泛型参数指定组件的实体
 * 2. 系统会为每个实体添加SystemState组件，用于标记初始化完成
 * 3. 可以通过addExtraRequireComponent添加额外的组件要求
 * 4. 系统会持续更新，直到所有符合条件的实体都完成初始化
 * <p>
 * 工作流程：
 * 1. 系统会查找包含指定组件但未初始化的实体
 * 2. 对每个实体调用onInitialize方法进行初始化
 * 3. 如果初始化成功，为实体添加SystemState组件标记
 * 4. 系统会持续运行，直到所有实体都完成初始化
 *
 * @param <T> 实体初始化所需的组件类型
 */
public abstract class EcsInitializeSystem<T extends EcsComponent> extends EcsSystem {
    public static abstract class SystemInitFinishSingle implements EcsComponent {}
    final private Class<T> entityClass;
    private final List<ComponentType<?>> extraRequirementComponent = new ArrayList<>();
    private final SystemInitFinishSingle systemInitFinishSingle;

    @SuppressWarnings("unchecked")
    public EcsInitializeSystem() {
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
        configEntityFilter(typeList);
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
        Collection<Entity> entityList = super.getAllMatchEntity();
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
    protected abstract SystemInitFinishSingle getInitFinishSingle();

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
