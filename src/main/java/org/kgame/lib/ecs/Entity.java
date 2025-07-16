package org.kgame.lib.ecs;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.kgame.lib.ecs.core.EcsEntityManager;
import org.kgame.lib.ecs.core.EntityArchetype;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class Entity implements EcsCleanable {
    private static final Logger logger = LogManager.getLogger(Entity.class);

    private final EcsEntityManager ecsEntityManager;
    private final int index;
    private final int type;
    private final Map<Class<? extends EcsComponent>, EcsComponent> data = new HashMap<>();

    private EntityArchetype archetype = EntityArchetype.EMPTY_INSTANCE;

    public Entity(EcsEntityManager ecsEntityManager, int index, int type) {
        this.ecsEntityManager = ecsEntityManager;
        this.index = index;
        this.type = type;
    }

    public Entity(EcsEntityManager ecsEntityManager, int index, int type, Collection<? extends EcsComponent> components) {
        this.ecsEntityManager = ecsEntityManager;
        this.index = index;
        this.type = type;
        for (EcsComponent component : components) {
            addComponentInstance(component);
        }
    }

    public void init() {
        this.archetype = ecsEntityManager.getOrCreateArchetype(data.keySet());
        archetype.addEntity(this);
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
    public void clean() {
        data.values().forEach(component -> {
            if (component instanceof EcsCleanable ecsCleanableComponent) {
                ecsCleanableComponent.clean();
            }
        });
        data.clear();
        getArchetype().removeEntity(this);
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

    public int getIndex() {
        return index;
    }

    public boolean hasComponent(Class<? extends EcsComponent> klass) {
        return data.containsKey(klass);
    }

    public void addComponent(EcsComponent component) {
        Class<? extends EcsComponent> componentClass = component.getClass();
        if (hasComponent(componentClass)) {
            logger.warn("add component failed! reason: component already exists of entity:{} componentType:{}",
                    getIndex(), componentClass.getSimpleName());
            return;
        }

        EntityArchetype oldArchetype = getArchetype();
        Set<Class<? extends EcsComponent>> newTypes = new HashSet<>(oldArchetype.getComponentTypes());
        newTypes.add(componentClass);
        updateArchetype(ecsEntityManager.getOrCreateArchetype(newTypes), oldArchetype);
        data.put(component.getClass(), component);
    }

    public void removeComponent(Class<? extends EcsComponent> componentCls) {
        EntityArchetype oldArchetype = getArchetype();
        Set<Class<? extends EcsComponent>> newTypes = new HashSet<>(oldArchetype.getComponentTypes());
        newTypes.remove(componentCls);
        updateArchetype(ecsEntityManager.getOrCreateArchetype(newTypes), oldArchetype);
        data.remove(componentCls);
    }

    private void addComponentInstance(EcsComponent component) {
        if (data.containsKey(component.getClass())) {
            return;
        }
        data.put(component.getClass(), component);
    }

    private <T extends EcsComponent>  T generateComponentByDefaultConstructor(Class<T> c) {
        try {
            return c.getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException("createEntity failed! component "
                    + c.getName() + " don't has default constructor", e);
        }
    }

    public int getType() {
        return type;
    }

    private void updateArchetype(EntityArchetype newArchetype, EntityArchetype oldArchetype) {
        newArchetype.addEntity(this);
        oldArchetype.removeEntity(this);
        archetype = newArchetype;
    }
}
