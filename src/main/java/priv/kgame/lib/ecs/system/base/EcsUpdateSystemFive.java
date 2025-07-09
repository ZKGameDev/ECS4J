package priv.kgame.lib.ecs.system.base;

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
import java.util.Collection;
import java.util.List;

/**
 * 五组件更新系统基类
 * <p>
 * 该类继承自EcsSystem，用于处理同时包含5个指定组件的实体的更新逻辑。
 * 特点：
 * 1. 系统会自动处理同时包含5个泛型参数指定组件的实体
 * 2. 系统会自动排除包含DestroyingComponent的实体
 * 3. 系统只处理包含InitializedComponent的实体
 * 4. 可以通过extraRequirementComponent添加额外的组件要求
 * <p>
 * 工作流程：
 * 1. 系统会查找同时包含5个指定组件、InitializedComponent，且不包含DestroyingComponent的实体
 * 2. 对每个实体调用update方法，传入实体和5个组件实例
 * 3. 在update方法中实现具体的更新逻辑
 * <p>
 *
 * @param <T1> 第一个必需的组件类型
 * @param <T2> 第二个必需的组件类型
 * @param <T3> 第三个必需的组件类型
 * @param <T4> 第四个必需的组件类型
 * @param <T5> 第五个必需的组件类型
 */
public abstract class EcsUpdateSystemFive<T1 extends EcsComponent,
        T2 extends EcsComponent, T3 extends EcsComponent, T4 extends EcsComponent, T5 extends EcsComponent> extends EcsLogicSystem {

    private ComponentType<T1> componentType1;
    private ComponentType<T2> componentType2;
    private ComponentType<T3> componentType3;
    private ComponentType<T4> componentType4;
    private ComponentType<T5> componentType5;

    @SuppressWarnings("unchecked")
    @Override
    protected Collection<ComponentType<?>> getMatchComponent() {
        Type[] parameterizedTypes = EcsTools.generateParameterizedType(this.getClass());
        componentType1 = ComponentType.additive(getWorld(), (Class<T1>) parameterizedTypes[0]);
        componentType2 = ComponentType.additive(getWorld(), (Class<T2>) parameterizedTypes[1]);
        componentType3 = ComponentType.additive(getWorld(), (Class<T3>) parameterizedTypes[2]);
        componentType4 = ComponentType.additive(getWorld(), (Class<T4>) parameterizedTypes[3]);
        componentType5 = ComponentType.additive(getWorld(), (Class<T5>) parameterizedTypes[4]);

        List<ComponentType<?>> componentTypes = new ArrayList<>();
        componentTypes.add(componentType1);
        componentTypes.add(componentType2);
        componentTypes.add(componentType3);
        componentTypes.add(componentType4);
        componentTypes.add(componentType5);
        componentTypes.add(ComponentType.subtractive(getWorld(), DestroyingComponent.class));
        componentTypes.add(ComponentType.additive(getWorld(), InitializedComponent.class));
        return componentTypes;
    }

    @Override
    protected void onUpdate() {
        for (Entity entity : super.getAllMatchEntity()) {
            update(entity, entity.getComponent(componentType1), entity.getComponent(componentType2)
                    , entity.getComponent(componentType3), entity.getComponent(componentType4), entity.getComponent(componentType5));
        }
    }

    protected abstract void update(Entity entity, T1 component, T2 component1, T3 component2, T4 component3, T5 component5);

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
