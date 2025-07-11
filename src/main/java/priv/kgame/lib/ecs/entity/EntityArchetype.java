package priv.kgame.lib.ecs.entity;

import priv.kgame.lib.ecs.Disposable;
import priv.kgame.lib.ecs.component.EcsComponent;

import java.util.*;

public class EntityArchetype implements Disposable {
    private final Set<Class<? extends EcsComponent>> componentMatchTypes = new HashSet<>();
    private final List<Entity> entityList = new ArrayList<>();

    public Set<Class<? extends EcsComponent>> getComponentTypes() {
        return componentMatchTypes;
    }

    public List<Entity> getEntityList() {
        return entityList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EntityArchetype that = (EntityArchetype) o;
        return componentMatchTypes.equals(that.componentMatchTypes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(componentMatchTypes);
    }

    @Override
    public void dispose() {
        componentMatchTypes.clear();
        entityList.clear();
    }

    public int size() {
        return entityList.size();
    }

    public void addComponentType(Class<? extends EcsComponent> componentMatchType) {
        componentMatchTypes.add(componentMatchType);
    }

    public boolean isSame(Collection<Class<? extends EcsComponent>> types) {
        return types.size() == componentMatchTypes.size() && componentMatchTypes.containsAll(types);
    }

    public boolean hasComponentType(Class<? extends EcsComponent> componentMatchType) {
        return componentMatchTypes.contains(componentMatchType);
    }

    public void addEntity(Entity entity) {
        entityList.add(entity);
    }

    public boolean removeEntity(Entity entity) {
        return entityList.remove(entity);
    }
}
