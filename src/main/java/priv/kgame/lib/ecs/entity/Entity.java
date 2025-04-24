package priv.kgame.lib.ecs.entity;

import priv.kgame.lib.ecs.Disposable;
import priv.kgame.lib.ecs.component.ComponentType;
import priv.kgame.lib.ecs.component.EcsComponent;
import priv.kgame.lib.ecs.component.RecycleComponent;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Entity implements Disposable {
    private final int index;
    private final int type;
    private final Map<ComponentType<? extends EcsComponent>, EcsComponent> data = new HashMap<>();
    private EntityArchetype archetype;

    public Entity(int index, int type, EntityArchetype archetype) {
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
        ComponentType<T> componentType = ComponentType.create(componentClass);
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
        return hasComponent(ComponentType.create(klass));
    }
    public boolean hasComponent(ComponentType<?> componentType) {
        return data.containsKey(componentType);
    }

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

    public void addComponent(ComponentType<?> componentType) {
        addComponent(componentType, componentType.generateComponentByDefaultConstructor());
    }

    public void removeComponent(ComponentType<?> componentType) {
        data.remove(componentType);
    }

    public boolean removeFromArchetype() {
        return getArchetype().removeEntity(this);
    }

    public void addComponent(EcsComponent component) {
        addComponent(ComponentType.create(component.getClass()), component);
    }

    public int getType() {
        return type;
    }
}
