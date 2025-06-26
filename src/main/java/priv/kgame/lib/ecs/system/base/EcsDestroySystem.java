package priv.kgame.lib.ecs.system.base;

import priv.kgame.lib.ecs.component.ComponentType;
import priv.kgame.lib.ecs.component.EcsComponent;
import priv.kgame.lib.ecs.component.base.DestroyingComponent;
import priv.kgame.lib.ecs.entity.Entity;
import priv.kgame.lib.ecs.entity.EntityGroup;
import priv.kgame.lib.ecs.system.EcsSystem;
import priv.kgame.lib.ecs.tools.EcsTools;

import java.lang.reflect.Type;
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

 * @param <T> 实体销毁处理所需的组件类型
 */
public abstract class EcsDestroySystem<T extends EcsComponent> extends EcsSystem {
    final private Class<T> genericClass;
    EntityGroup group;

    @SuppressWarnings("unchecked")
    public EcsDestroySystem(){
        Type[] parameterizedTypes = EcsTools.generateParameterizedType(this.getClass());
        genericClass = (Class<T>) parameterizedTypes[0];
    }

    @Override
    protected void onInit() {
        group = getOrAddEntityGroup(ComponentType.additive(getWorld(), genericClass),
                ComponentType.additive(getWorld(), DestroyingComponent.class));
        super.setAlwaysUpdateSystem(true);
    }

    @Override
    protected void onUpdate() {
        List<Entity> entities = group.getEntityList();
        for (Entity entity : entities) {
            ComponentType<T> componentType = ComponentType.additive(getWorld(), genericClass);
            entity.assertContainComponent(componentType);
            onEntityDestroy(entity, entity.getComponent(componentType));
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
