package priv.kgame.lib.ecs.core;

import priv.kgame.lib.ecs.EcsCleanable;
import priv.kgame.lib.ecs.EcsComponent;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class ComponentTypeQuery implements EcsCleanable {
    private final Set<Class<? extends EcsComponent>> any = new HashSet<>();
    //不能包含任何一个
    private final Set<Class<? extends EcsComponent>> none = new HashSet<>();
    //所有的都要包含
    private final Set<Class<? extends EcsComponent>> all = new HashSet<>();

    @Override
    public void clean() {
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
        if (!checkMatchingAll(entityArchetype)) {
            return false;
        }
        if (!checkMatchingNone(entityArchetype)) {
            return false;
        }
        return checkMatchingAny(entityArchetype);
    }

    private boolean checkMatchingAll(EntityArchetype entityArchetype) {
        for (Class<? extends EcsComponent> componentMatchType : all) {
            if (!entityArchetype.hasComponent(componentMatchType)) {
                return false;
            }
        }
        return true;
    }

    private boolean checkMatchingNone(EntityArchetype entityArchetype) {
        for (Class<? extends EcsComponent> componentMatchType : entityArchetype.getComponentTypes()) {
            if (none.contains(componentMatchType)) {
                return false;
            }
        }
        return true;
    }

    private boolean checkMatchingAny(EntityArchetype entityArchetype) {
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
            if (type.getAccessModeType() == ComponentAccessMode.NONE) {
                result.addNone(type.getType());
            } else {
                result.addAll(type.getType());
            }
        }
        return result;
    }
}
