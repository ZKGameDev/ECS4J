package priv.kgame.lib.ecs.system.base;

import priv.kgame.lib.ecs.EcsWorld;
import priv.kgame.lib.ecs.component.ComponentType;
import priv.kgame.lib.ecs.component.EcsComponent;
import priv.kgame.lib.ecs.component.base.DestroyingComponent;
import priv.kgame.lib.ecs.component.base.InitializedComponent;
import priv.kgame.lib.ecs.entity.Entity;
import priv.kgame.lib.ecs.entity.EntityGroup;
import priv.kgame.lib.ecs.system.EcsSystem;
import priv.kgame.lib.ecs.tools.EcsTools;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * 三组件更新系统基类
 * <p>
 * 该类继承自EcsSystem，用于处理同时包含3个指定组件的实体的更新逻辑。
 * 特点：
 * 1. 系统会自动处理同时包含3个泛型参数指定组件的实体
 * 2. 系统会自动排除包含DestroyingComponent的实体
 * 3. 系统只处理包含InitializedComponent的实体
 * 4. 可以通过extraRequirementComponent添加额外的组件要求
 * <p>
 * 工作流程：
 * 1. 系统会查找同时包含3个指定组件、InitializedComponent，且不包含DestroyingComponent的实体
 * 2. 对每个实体调用update方法，传入实体和3个组件实例
 * 3. 在update方法中实现具体的更新逻辑
 * <p>
 * @param <T1> 第一个必需的组件类型
 * @param <T2> 第二个必需的组件类型
 * @param <T3> 第三个必需的组件类型
 */
public abstract class EcsUpdateSystemThree <T1 extends EcsComponent,
        T2 extends EcsComponent, T3 extends EcsComponent> extends EcsSystem {

    private final Class<T1> componentClass1;
    private final Class<T2> componentClass2;
    private final Class<T3> componentClass3;

    protected List<ComponentType<?>> extraRequirementComponent = new ArrayList<>();
    private EntityGroup entityGroup;

    @SuppressWarnings("unchecked")
    public EcsUpdateSystemThree() {
        Type[] parameterizedTypes = EcsTools.generateParameterizedType(this.getClass());
        componentClass1 = (Class<T1>) parameterizedTypes[0];
        componentClass2 = (Class<T2>) parameterizedTypes[1];
        componentClass3 = (Class<T3>) parameterizedTypes[2];
    }

    @Override
    protected void onInit() {
        List<ComponentType<?>> componentTypes = new ArrayList<>();
        componentTypes.add(ComponentType.additive(getWorld(), componentClass1));
        componentTypes.add(ComponentType.additive(getWorld(), componentClass2));
        componentTypes.add(ComponentType.additive(getWorld(), componentClass3));
        if (!extraRequirementComponent.isEmpty()) {
            componentTypes.addAll(extraRequirementComponent);
        }
        componentTypes.add(ComponentType.subtractive(getWorld(), DestroyingComponent.class));
        componentTypes.add(ComponentType.additive(getWorld(), InitializedComponent.class));
        entityGroup = getOrAddEntityGroup(componentTypes);
    }

    @Override
    protected void onUpdate() {
        for (Entity entity : entityGroup.getEntityList()) {
            ComponentType<T1> componentType1 = ComponentType.additive(getWorld(), componentClass1);
            ComponentType<T2> componentType2 = ComponentType.additive(getWorld(), componentClass2);
            ComponentType<T3> componentType3 = ComponentType.additive(getWorld(), componentClass3);
            entity.assertContainComponent(componentType1);
            entity.assertContainComponent(componentType2);
            update(entity, entity.getComponent(componentType1), entity.getComponent(componentType2)
                    , entity.getComponent(componentType3));
        }
    }

    protected abstract void update(Entity entity, T1 component1, T2 component2, T3 component3);

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
