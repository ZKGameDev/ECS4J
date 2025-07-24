package top.kgame.lib.ecs.extensions.system;

import top.kgame.lib.ecs.core.ComponentMatchParam;
import top.kgame.lib.ecs.EcsComponent;
import top.kgame.lib.ecs.extensions.component.DestroyingComponent;
import top.kgame.lib.ecs.Entity;
import top.kgame.lib.ecs.tools.EcsTools;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 实体销毁系统基类
 * <p>
 * 自动处理包含指定组件和DestroyingComponent的实体销毁逻辑。
 * 
 * @param <T> 销毁处理所需的组件类型
 */
public abstract class EcsDestroySystem<T extends EcsComponent> extends EcsLogicSystem {
    private ComponentMatchParam<T> matchComponentMatchType;

    @SuppressWarnings("unchecked")
    @Override
    protected Collection<ComponentMatchParam<?>> getMatchComponent() {
        Type[] parameterizedTypes = EcsTools.generateParameterizedType(this.getClass());
        matchComponentMatchType = ComponentMatchParam.additive(getWorld(), (Class<T>) parameterizedTypes[0]);

        List<ComponentMatchParam<?>> typeList = new ArrayList<>();
        typeList.add(matchComponentMatchType);
        typeList.add(ComponentMatchParam.additive(getWorld(), DestroyingComponent.class));
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
