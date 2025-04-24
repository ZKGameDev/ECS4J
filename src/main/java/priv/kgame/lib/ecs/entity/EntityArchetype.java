package priv.kgame.lib.ecs.entity;

import priv.kgame.lib.ecs.Disposable;
import priv.kgame.lib.ecs.component.ComponentType;

import java.util.*;

public class EntityArchetype implements Disposable {
    private final Set<ComponentType<?>> componentTypes = new HashSet<>();
    private final List<Entity> entityList = new ArrayList<>();

    public Set<ComponentType<?>> getComponentTypes() {
        return componentTypes;
    }

    public List<Entity> getEntityList() {
        return entityList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EntityArchetype that = (EntityArchetype) o;
        return componentTypes.equals(that.componentTypes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(componentTypes);
    }

    @Override
    public void dispose() {
        componentTypes.clear();
        entityList.clear();
    }

    public int size() {
        return entityList.size();
    }

    public void addComponentType(ComponentType<?> componentType) {
        componentTypes.add(componentType);
    }

    public boolean isSame(Collection<ComponentType<?>> types) {
        return types.size() == componentTypes.size() && componentTypes.containsAll(types);
    }

    public void addEntity(Entity entity) {
        entityList.add(entity);
    }

    public boolean removeEntity(Entity entity) {
        return entityList.remove(entity);
    }
}
