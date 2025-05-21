package priv.kgame.lib.ecs.entity;

import priv.kgame.lib.ecs.Disposable;
import priv.kgame.lib.ecs.EcsWorld;
import priv.kgame.lib.ecs.component.ComponentType;
import priv.kgame.lib.ecs.component.EcsComponent;
import priv.kgame.lib.ecs.component.RecycleComponent;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Entity implements Disposable {
    private final EcsWorld world;
    private final int index;
    private final int type;
    private final Map<ComponentType<? extends EcsComponent>, EcsComponent> data = new HashMap<>();
    private EntityArchetype archetype;

    public Entity(EcsWorld ecsWorld, int index, int type, EntityArchetype archetype) {
        this.world = ecsWorld;
        this.index = index;
        this.type = type;
        this.archetype = archetype;
    }

    public Map<ComponentType<?>, EcsComponent> getData() {
        return data;
    }

    @SuppressWarnings({"unchecked"})
    public <T extends EcsComponent> T getComponent(ComponentType<T> componentType) {
        EcsComponent ecsComponent = data.get(componentType);
        if (null == ecsComponent) {
            return null;
        }
        return (T)ecsComponent;
    }

    public <T extends EcsComponent> T getComponent(Class<T> componentClass) {
        ComponentType<T> componentType = ComponentType.additive(world, componentClass);
        return getComponent(componentType);
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

    public void assertContainComponent(ComponentType<?> componentType) {
        if (!getData().containsKey(componentType)) {
            throw new RuntimeException(String.format("EcsEndSystem update failed! %s not exist in entity. please check code", componentType.getType()));
        }
    }

    public int getIndex() {
        return index;
    }

    public boolean hasComponent(Class<? extends EcsComponent> klass) {
        return hasComponent(ComponentType.additive(world, klass));
    }
    public boolean hasComponent(ComponentType<?> componentType) {
        return data.containsKey(componentType);
    }

    /**
     * 添加组件到实体中，并验证组件类型是否匹配。
     * 注意：此方法仅供 ECS 框架内部使用，外部代码不应直接调用。
     */
    public void addComponent(ComponentType<?> componentType, EcsComponent component) {
        if (!componentType.getType().equals(component.getClass())) {
            throw new RuntimeException(String.format("EcsEndSystem addComponent failed! component %s not match ComponentType %S. please check code",
                    component.getClass().getName(), componentType.getType().getName()));
        }
        if (data.containsKey(componentType)) {
            return;
        }
        data.put(componentType, component);
    }

    /**
     * 使用组件类型的默认构造函数创建并添加组件。
     * 注意：此方法仅供 ECS 框架内部使用，外部代码不应直接调用。
     */
    public void addComponent(ComponentType<?> componentType) {
        addComponent(componentType, componentType.generateComponentByDefaultConstructor());
    }

    /**
     * 添加组件到实体中。
     * 注意：此方法仅供 ECS 框架内部使用，外部代码不应直接调用。
     */
    public void addComponent(EcsComponent component) {
        addComponent(ComponentType.additive(world, component.getClass()), component);
    }

    /**
     * 从实体中移除指定类型的组件。
     * 注意：此方法仅供 ECS 框架内部使用，外部代码不应直接调用。
     */
    public void removeComponent(ComponentType<?> componentType) {
        data.remove(componentType);
    }

    public boolean removeFromArchetype() {
        return getArchetype().removeEntity(this);
    }

    public int getType() {
        return type;
    }
}
