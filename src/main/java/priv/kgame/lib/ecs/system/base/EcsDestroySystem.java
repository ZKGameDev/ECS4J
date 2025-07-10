package priv.kgame.lib.ecs.system.base;

import priv.kgame.lib.ecs.component.ComponentMatchType;
import priv.kgame.lib.ecs.component.EcsComponent;
import priv.kgame.lib.ecs.component.base.DestroyingComponent;
import priv.kgame.lib.ecs.entity.Entity;
import priv.kgame.lib.ecs.tools.EcsTools;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 实体销毁系统基类
 * <p>
 * 该类继承自EcsSystem，用于处理实体的销毁逻辑。
 * 特点：
 * 1. 系统会自动处理同时包含泛型参数T指定组件和DestroyingComponent的实体
 * 2. 系统被设置为alwaysUpdateSystem，会在每次世界更新时执行
 * 3. 对每个符合条件的实体调用onEntityDestroy方法进行销毁处理
 * <p>
 * 工作流程：
 * 1. 系统会查找同时包含T类型组件和DestroyingComponent的实体
 * 2. 对每个实体调用onEntityDestroy方法，传入实体和T类型组件
 * 3. 在onEntityDestroy方法中实现具体的销毁逻辑
 * <p>
 *
 * @param <T> 实体销毁处理所需的组件类型
 */
public abstract class EcsDestroySystem<T extends EcsComponent> extends EcsLogicSystem {
    private ComponentMatchType<T> matchComponentMatchType;

    @SuppressWarnings("unchecked")
    @Override
    protected Collection<ComponentMatchType<?>> getMatchComponent() {
        Type[] parameterizedTypes = EcsTools.generateParameterizedType(this.getClass());
        matchComponentMatchType = ComponentMatchType.additive(getWorld(), (Class<T>) parameterizedTypes[0]);

        List<ComponentMatchType<?>> typeList = new ArrayList<>();
        typeList.add(matchComponentMatchType);
        typeList.add(ComponentMatchType.additive(getWorld(), DestroyingComponent.class));
        return typeList;
    }

    @Override
    protected void onUpdate() {
        Collection<Entity> entities = super.getAllMatchEntity();
        for (Entity entity : entities) {
            onEntityDestroy(entity, entity.getComponent(matchComponentMatchType.getType()));
        }
    }

    protected abstract void onEntityDestroy(Entity entity, T component);

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
