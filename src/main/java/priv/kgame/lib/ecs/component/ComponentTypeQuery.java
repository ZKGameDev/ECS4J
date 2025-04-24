package priv.kgame.lib.ecs.component;

import priv.kgame.lib.ecs.Disposable;

import java.util.Objects;
import java.util.TreeSet;

public class ComponentTypeQuery implements Disposable {
    private final TreeSet<ComponentType<?>> any = new TreeSet<>(ComponentType::compareTo);
    private final TreeSet<ComponentType<?>> none = new TreeSet<>(ComponentType::compareTo);
    private final TreeSet<ComponentType<?>> all = new TreeSet<>(ComponentType::compareTo);

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

    public TreeSet<ComponentType<?>> getAny() {
        return any;
    }

    public TreeSet<ComponentType<?>> getNone() {
        return none;
    }

    public TreeSet<ComponentType<?>> getAll() {
        return all;
    }

    public void addNone(ComponentType<?> type) {
        none.add(type);
    }

    public void addAll(ComponentType<?> type) {
        all.add(type);
    }
}
