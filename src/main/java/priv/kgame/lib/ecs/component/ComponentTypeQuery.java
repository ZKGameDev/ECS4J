package priv.kgame.lib.ecs.component;

import priv.kgame.lib.ecs.Disposable;
import priv.kgame.lib.ecs.entity.EntityArchetype;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class ComponentTypeQuery implements Disposable {
    private final Set<Class<? extends EcsComponent>> any = new HashSet<>();
    private final Set<Class<? extends EcsComponent>> none = new HashSet<>();
    //所有都要滿足
    private final Set<Class<? extends EcsComponent>> all = new HashSet<>();

    @Override
    public void dispose() {
        any.clear();
        none.clear();
        all.clear();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ComponentTypeQuery that = (ComponentTypeQuery) o;
        return any.equals(that.any) && none.equals(that.none) && all.equals(that.all);
    }

    @Override
    public int hashCode() {
        return Objects.hash(any, none, all);
    }

    public Set<Class<? extends EcsComponent>> getAny() {
        return any;
    }

    public Set<Class<? extends EcsComponent>> getNone() {
        return none;
    }

    public Set<Class<? extends EcsComponent>> getAll() {
        return all;
    }

    public void addNone(Class<? extends EcsComponent> type) {
        none.add(type);
    }

    public void addAll(Class<? extends EcsComponent> type) {
        all.add(type);
    }

    public boolean isMatchingArchetype(EntityArchetype entityArchetype) {
        if (!checkMatchingArchetypeAll(entityArchetype)) {
            return false;
        }
        if (!checkMatchingArchetypeNone(entityArchetype)) {
            return false;
        }
        return checkMatchingArchetypeAny(entityArchetype);
    }

    private boolean checkMatchingArchetypeAll(EntityArchetype entityArchetype) {
        for (Class<? extends EcsComponent> componentMatchType : all) {
            if (!entityArchetype.hasComponentType(componentMatchType)) {
                return false;
            }
        }
        return true;
    }

    private boolean checkMatchingArchetypeNone(EntityArchetype entityArchetype) {
        for (Class<? extends EcsComponent> componentMatchType : entityArchetype.getComponentTypes()) {
            if (none.contains(componentMatchType)) {
                return false;
            }
        }
        return true;
    }

    private boolean checkMatchingArchetypeAny(EntityArchetype entityArchetype) {
        if (any.isEmpty()) {
            return true;
        }
        for (Class<? extends EcsComponent> componentMatchType : entityArchetype.getComponentTypes()) {
            if (any.contains(componentMatchType)) {
                return true;
            }
        }
        return false;
    }

    public static ComponentTypeQuery generate(Collection<ComponentMatchParam<?>> componentMatchTypes) {
        ComponentTypeQuery result = new ComponentTypeQuery();
        for (ComponentMatchParam<?> type : componentMatchTypes) {
            if (type.getAccessModeType() == ComponentAccessMode.SUBTRACTIVE) {
                result.addNone(type.getType());
            } else {
                result.addAll(type.getType());
            }
        }
        return result;
    }
}
