package priv.kgame.lib.ecs.extensions.system;

import priv.kgame.lib.ecs.core.ComponentMatchParam;
import priv.kgame.lib.ecs.EcsComponent;
import priv.kgame.lib.ecs.extensions.component.DestroyingComponent;
import priv.kgame.lib.ecs.Entity;
import priv.kgame.lib.ecs.tools.EcsTools;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 排除单个组件的更新系统基类
 * <p>
 * 该类继承自EcsSystem，用于实现排除特定组件的实体更新系统。
 * 特点：
 * 1. 系统会自动排除泛型参数指定的组件类型
 * 2. 系统会自动排除DespawningComponent组件
 * 3. 可以通过extraRequirementComponent添加额外的组件要求
 * 4. 每次更新时会遍历所有符合条件的实体，并对每个实体执行update方法
 * <p>
 *
 * @param <T> 要排除的组件类型
 */
public abstract class EcsUpdateSystemExcludeOne<T extends EcsComponent> extends EcsLogicSystem {
    @SuppressWarnings("unchecked")
    @Override
    protected Collection<ComponentMatchParam<?>> getMatchComponent() {
        Type[] parameterizedTypes = EcsTools.generateParameterizedType(this.getClass());
        ComponentMatchParam<T> matchComponentMatchType = ComponentMatchParam.subtractive(getWorld(), (Class<T>) parameterizedTypes[0]);

        List<ComponentMatchParam<?>> typeList = new ArrayList<>();
        typeList.add(matchComponentMatchType);
        typeList.add(ComponentMatchParam.subtractive(getWorld(), DestroyingComponent.class));
        return typeList;
    }

    @Override
    protected void onUpdate() {
        Collection<Entity> entities = super.getAllMatchEntity();
        for (Entity entity : entities) {
            update(entity);
        }
    }

    protected abstract void update(Entity entity);

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
