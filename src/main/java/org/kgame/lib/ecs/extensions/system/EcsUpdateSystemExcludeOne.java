package org.kgame.lib.ecs.extensions.system;

import org.kgame.lib.ecs.EcsComponent;
import org.kgame.lib.ecs.Entity;
import org.kgame.lib.ecs.core.ComponentMatchParam;
import org.kgame.lib.ecs.tools.EcsTools;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 排除单个组件的更新系统基类
 * <p>
 * 用于实现排除特定组件的实体更新系统。
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
