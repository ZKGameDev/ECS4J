package org.kgame.lib.ecs.extensions.system;

import org.kgame.lib.ecs.core.ComponentMatchParam;
import org.kgame.lib.ecs.EcsComponent;
import org.kgame.lib.ecs.Entity;
import org.kgame.lib.ecs.tools.EcsTools;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 实体初始化系统基类
 * <p>
 * 自动处理包含指定组件的实体初始化，为每个实体添加SystemState组件标记初始化完成。
 * 
 * @param <T> 实体初始化所需的组件类型
 */
public abstract class EcsInitializeSystem<T extends EcsComponent> extends EcsLogicSystem {
    public static abstract class SystemInitFinishSingle implements EcsComponent {}

    private ComponentMatchParam<T> matchComponentMatchType;
    private SystemInitFinishSingle systemInitFinishSingle;

    public EcsInitializeSystem() {}

    @Override
    protected void onInit() {
        systemInitFinishSingle = getInitFinishSingle();
        super.onInit();
    }

    @Override
    protected void onUpdate() {
        Collection<Entity> entityList = super.getAllMatchEntity();
        for (Entity entity : entityList) {
            if (onInitialize(entity, entity.getComponent(matchComponentMatchType.getType()))) {
                entity.addComponent(systemInitFinishSingle);
            }
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    protected Collection<ComponentMatchParam<?>> getMatchComponent() {
        Type[] parameterizedTypes = EcsTools.generateParameterizedType(this.getClass());
        matchComponentMatchType = ComponentMatchParam.additive(getWorld(), (Class<T>) parameterizedTypes[0]);

        List<ComponentMatchParam<?>> typeList = new ArrayList<>();
        typeList.add(matchComponentMatchType);
        typeList.add(ComponentMatchParam.subtractive(getWorld(), systemInitFinishSingle.getClass()));
        return typeList;
    }

    @Override
    protected void onStart() {}

    @Override
    protected void onStop() {}

    @Override
    protected void onDestroy() {}

    public abstract boolean onInitialize(Entity entity, T data);

    protected abstract SystemInitFinishSingle getInitFinishSingle();
}
