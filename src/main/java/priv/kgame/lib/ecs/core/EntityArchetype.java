package priv.kgame.lib.ecs.core;

import priv.kgame.lib.ecs.EcsCleanable;
import priv.kgame.lib.ecs.EcsComponent;
import priv.kgame.lib.ecs.Entity;

import java.util.*;

public class EntityArchetype implements EcsCleanable {
    public static final EntityArchetype EMPTY_INSTANCE = new EMPTY();
    private final Set<Class<? extends EcsComponent>> componentMatchTypes = new HashSet<>();
    private final List<Entity> entityList = new ArrayList<>();

    private static class EMPTY extends EntityArchetype {
        @Override
        public void addComponent(Class<? extends EcsComponent> componentMatchType) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void addEntity(Entity entity) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean removeEntity(Entity entity) {
            throw new UnsupportedOperationException();
        }
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
    public void clean() {
        componentMatchTypes.clear();
        entityList.clear();
    }

    public Set<Class<? extends EcsComponent>> getComponentTypes() {
        return componentMatchTypes;
    }

    public List<Entity> getEntityList() {
        return entityList;
    }

    public int size() {
        return entityList.size();
    }

    public void addComponent(Class<? extends EcsComponent> componentClass) {
        componentMatchTypes.add(componentClass);
    }

    public boolean isSame(Collection<Class<? extends EcsComponent>> types) {
        return types.size() == componentMatchTypes.size() && componentMatchTypes.containsAll(types);
    }

    public boolean hasComponent(Class<? extends EcsComponent> componentClass) {
        return componentMatchTypes.contains(componentClass);
    }

    public void addEntity(Entity entity) {
        entityList.add(entity);
    }

    public boolean removeEntity(Entity entity) {
        return entityList.remove(entity);
    }
}
