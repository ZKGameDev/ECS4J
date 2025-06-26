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
 * @param <T> 实体更新处理所需的组件类型
 */
public abstract class EcsUpdateSystemOne<T extends EcsComponent> extends EcsSystem {
    private final Class<T> entityClass;
    private EntityGroup entityGroup;
    protected List<ComponentType<?>> extraRequirementComponent = new ArrayList<>();

    @SuppressWarnings("unchecked")
    public EcsUpdateSystemOne() {
        Type[] parameterizedTypes = EcsTools.generateParameterizedType(this.getClass());
        entityClass = (Class<T>) parameterizedTypes[0];
    }

    @Override
    protected void onInit() {
        List<ComponentType<?>> typeList = new ArrayList<>();
        typeList.add(ComponentType.additive(getWorld(), entityClass));
        if (!extraRequirementComponent.isEmpty()) {
            typeList.addAll(extraRequirementComponent);
        }
        typeList.add(ComponentType.subtractive(getWorld(), DestroyingComponent.class));
        typeList.add(ComponentType.additive(getWorld(), InitializedComponent.class));
        entityGroup = getOrAddEntityGroup(typeList);
    }

    @Override
    protected void onUpdate() {
        List<Entity> entities = entityGroup.getEntityList();
        for (Entity entity : entities) {
            ComponentType<T> componentType = ComponentType.additive(getWorld(), entityClass);
            entity.assertContainComponent(componentType);
            update(entity, entity.getComponent(componentType));
        }
    }

    protected abstract void update(Entity entity, T component);

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
