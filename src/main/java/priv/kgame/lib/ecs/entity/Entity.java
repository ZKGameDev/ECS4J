package priv.kgame.lib.ecs.entity;

import priv.kgame.lib.ecs.Disposable;
import priv.kgame.lib.ecs.EcsWorld;
import priv.kgame.lib.ecs.component.EcsComponent;
import priv.kgame.lib.ecs.component.RecycleComponent;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Entity implements Disposable {
    private final EcsWorld world;
    private final int index;
    private final int type;
    private final Map<Class<? extends EcsComponent>, EcsComponent> data = new HashMap<>();
    private EntityArchetype archetype;

    public Entity(EcsWorld ecsWorld, int index, int type, EntityArchetype archetype) {
        this.world = ecsWorld;
        this.index = index;
        this.type = type;
        this.archetype = archetype;
    }

    @SuppressWarnings({"unchecked"})
    public <T extends EcsComponent> T getComponent(Class<T> componentClass) {
        EcsComponent ecsComponent = data.get(componentClass);
        if (null == ecsComponent) {
            return null;
        }
        return (T)ecsComponent;
    }

    @Override
    public void dispose() {
        data.values().forEach(component -> {
            if (component instanceof RecycleComponent<?> recycleComponent) {
                recycleComponent.clear();
            }
        });
        data.clear();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Entity entity = (Entity) o;
        return index == entity.index;
    }

    @Override
    public int hashCode() {
        return Objects.hash(index);
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("Entity{");
        result.append("index=").append(index);
        result.append("archetype=").append(archetype);
        result.append("data=[");
        for (EcsComponent component : data.values()) {
            if (null == component) {
                continue;
            }
            result.append(component.getClass().getSimpleName()).append(",");
        }
        result.append("]");
        return result.toString();
    }

    public EntityArchetype getArchetype() {
        return archetype;
    }

    public void setArchetype(EntityArchetype archetype) {
        this.archetype = archetype;
    }

    public int getIndex() {
        return index;
    }

    public boolean hasComponent(Class<? extends EcsComponent> klass) {
        return data.containsKey(klass);
    }

    /**
     * 添加组件到实体中，并验证组件类型是否匹配。
     * 注意：此方法仅供 ECS 框架内部使用，外部代码不应直接调用。
     */
    public void addComponent(EcsComponent component) {
        if (data.containsKey(component.getClass())) {
            return;
        }
        data.put(component.getClass(), component);
    }

    /**
     * 使用组件类型的默认构造函数创建并添加组件。
     * 注意：此方法仅供 ECS 框架内部使用，外部代码不应直接调用。
     */
    public void addComponent(Class<? extends EcsComponent> componentClass) {
        addComponent(generateComponentByDefaultConstructor(componentClass));
    }

    /**
     * 通过type的默认构造函数生成对应的Component实例
     * @return type对应的Component实例
     * @throws RuntimeException 当type没有默认构造函数时抛出
     */
    private <T extends EcsComponent>  T generateComponentByDefaultConstructor(Class<T> c) {
        try {
            return c.getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException("createEntity failed! component "
                    + c.getName() + " don't has default constructor", e);
        }
    }

    /**
     * 从实体中移除指定类型的组件。
     * 注意：此方法仅供 ECS 框架内部使用，外部代码不应直接调用。
     */
    public void removeComponent(Class<? extends EcsComponent> componentClass) {
        data.remove(componentClass);
    }

    public boolean removeFromArchetype() {
        return getArchetype().removeEntity(this);
    }

    public int getType() {
        return type;
    }

    public EcsWorld getWorld() {
        return world;
    }
}
