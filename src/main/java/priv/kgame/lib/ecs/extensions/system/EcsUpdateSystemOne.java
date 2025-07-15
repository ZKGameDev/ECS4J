package priv.kgame.lib.ecs.extensions.system;

import priv.kgame.lib.ecs.EcsComponent;
import priv.kgame.lib.ecs.Entity;
import priv.kgame.lib.ecs.core.ComponentMatchParam;
import priv.kgame.lib.ecs.tools.EcsTools;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * 单组件更新系统基类
 * <p>
 * 该类继承自EcsSystem，用于处理包含单个指定组件的实体的更新逻辑。
 * 特点：
 * 1. 系统会自动处理包含泛型参数T指定组件的实体
 * 2. 系统会自动排除包含DestroyingComponent的实体
 * 3. 系统只处理包含InitializedComponent的实体
 * 4. 可以通过extraRequirementComponent添加额外的组件要求
 * <p>
 * 工作流程：
 * 1. 系统会查找同时包含T类型组件、InitializedComponent，且不包含DestroyingComponent的实体
 * 2. 对每个实体调用update方法，传入实体和T类型组件实例
 * 3. 在update方法中实现具体的更新逻辑
 * <p>
 *
 * @param <T> 实体更新处理所需的组件类型
 */
public abstract class EcsUpdateSystemOne<T extends EcsComponent> extends EcsLogicSystem {
    private ComponentMatchParam<T> matchComponentMatchType;

    @SuppressWarnings("unchecked")
    @Override
    protected Collection<ComponentMatchParam<?>> getMatchComponent() {
        Type[] parameterizedTypes = EcsTools.generateParameterizedType(this.getClass());
        matchComponentMatchType = ComponentMatchParam.additive(getWorld(), (Class<T>) parameterizedTypes[0]);

        List<ComponentMatchParam<?>> typeList = new ArrayList<>();
        typeList.add(matchComponentMatchType);
        return typeList;
    }

    @Override
    protected void onUpdate() {
        Collection<Entity> entities = super.getAllMatchEntity();
        for (Entity entity : entities) {
            update(entity, entity.getComponent(matchComponentMatchType.getType()));
        }
    }

    protected abstract void update(Entity entity, T component);

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
