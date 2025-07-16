package org.kgame.lib.ecs.extensions.system;

import org.kgame.lib.ecs.EcsComponent;
import org.kgame.lib.ecs.Entity;
import org.kgame.lib.ecs.core.ComponentMatchParam;
import org.kgame.lib.ecs.tools.EcsTools;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
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
 *
 * @param <T1> 第一个必需的组件类型
 * @param <T2> 第二个必需的组件类型
 * @param <T3> 第三个必需的组件类型
 */
public abstract class EcsUpdateSystemThree<T1 extends EcsComponent,
        T2 extends EcsComponent, T3 extends EcsComponent> extends EcsLogicSystem {
    private ComponentMatchParam<T1> componentMatchType1;
    private ComponentMatchParam<T2> componentMatchType2;
    private ComponentMatchParam<T3> componentMatchType3;

    @SuppressWarnings("unchecked")
    @Override
    protected Collection<ComponentMatchParam<?>> getMatchComponent() {
        Type[] parameterizedTypes = EcsTools.generateParameterizedType(this.getClass());
        componentMatchType1 = ComponentMatchParam.additive(getWorld(), (Class<T1>) parameterizedTypes[0]);
        componentMatchType2 = ComponentMatchParam.additive(getWorld(), (Class<T2>) parameterizedTypes[1]);
        componentMatchType3 = ComponentMatchParam.additive(getWorld(), (Class<T3>) parameterizedTypes[2]);

        List<ComponentMatchParam<?>> componentMatchTypes = new ArrayList<>();
        componentMatchTypes.add(componentMatchType1);
        componentMatchTypes.add(componentMatchType2);
        componentMatchTypes.add(componentMatchType3);
        return componentMatchTypes;
    }

    @Override
    protected void onUpdate() {
        for (Entity entity : super.getAllMatchEntity()) {
            update(entity, entity.getComponent(componentMatchType1.getType()),
                    entity.getComponent(componentMatchType2.getType()),
                    entity.getComponent(componentMatchType3.getType()));
        }
    }

    protected abstract void update(Entity entity, T1 component1, T2 component2, T3 component3);

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
