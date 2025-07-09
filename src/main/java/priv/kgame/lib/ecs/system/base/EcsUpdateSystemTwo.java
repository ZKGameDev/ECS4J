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
import java.util.Collections;
import java.util.List;

/**
 * 双组件更新系统基类
 * <p>
 * 该类继承自EcsSystem，用于处理同时包含2个指定组件的实体的更新逻辑。
 * 特点：
 * 1. 系统会自动处理同时包含2个泛型参数指定组件的实体
 * 2. 系统会自动排除包含DestroyingComponent的实体
 * 3. 系统只处理包含InitializedComponent的实体
 * 4. 可以通过extraRequirementComponent添加额外的组件要求
 * <p>
 * 工作流程：
 * 1. 系统会查找同时包含2个指定组件、InitializedComponent，且不包含DestroyingComponent的实体
 * 2. 对每个实体调用update方法，传入实体和2个组件实例
 * 3. 在update方法中实现具体的更新逻辑
 * <p>
 * @param <T1> 第一个必需的组件类型
 * @param <T2> 第二个必需的组件类型
 */
public abstract class EcsUpdateSystemTwo<T1 extends EcsComponent, T2 extends EcsComponent> extends EcsLogicSystem {
    private ComponentType<T1> componentType1;
    private ComponentType<T2> componentType2;

    @SuppressWarnings("unchecked")
    @Override
    protected Collection<ComponentType<?>> getMatchComponent() {
        Type[] parameterizedTypes = EcsTools.generateParameterizedType(this.getClass());
        componentType1 = ComponentType.additive(getWorld(), (Class<T1>) parameterizedTypes[0]);
        componentType2 = ComponentType.additive(getWorld(), (Class<T2>) parameterizedTypes[1]);

        List<ComponentType<?>> componentTypes = new ArrayList<>();
        componentTypes.add(componentType1);
        componentTypes.add(componentType2);
        componentTypes.add(ComponentType.subtractive(getWorld(), DestroyingComponent.class));
        componentTypes.add(ComponentType.additive(getWorld(), InitializedComponent.class));
        return componentTypes;
    }

    @Override
    protected void onUpdate() {
        for (Entity entity : super.getAllMatchEntity()) {
            update(entity, entity.getComponent(componentType1), entity.getComponent(componentType2));
        }
    }

    protected abstract void update(Entity entity, T1 component, T2 component1);

    @Override
    public Collection<Class<? extends EcsComponent>> getExtraRequirementComponent() {
        return Collections.emptyList();
    }

    @Override
    public Collection<Class<? extends EcsComponent>> getExtraExcludeComponent() {
        return Collections.emptyList();
    }

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
