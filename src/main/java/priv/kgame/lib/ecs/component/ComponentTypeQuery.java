package priv.kgame.lib.ecs.component;

import priv.kgame.lib.ecs.Disposable;

import java.util.Objects;
import java.util.TreeSet;

public class ComponentTypeQuery implements Disposable {
    private final TreeSet<ComponentMatchType<?>> any = new TreeSet<>(ComponentMatchType::compareTo);
    private final TreeSet<ComponentMatchType<?>> none = new TreeSet<>(ComponentMatchType::compareTo);
    private final TreeSet<ComponentMatchType<?>> all = new TreeSet<>(ComponentMatchType::compareTo);

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

    public TreeSet<ComponentMatchType<?>> getAny() {
        return any;
    }

    public TreeSet<ComponentMatchType<?>> getNone() {
        return none;
    }

    public TreeSet<ComponentMatchType<?>> getAll() {
        return all;
    }

    public void addNone(ComponentMatchType<?> type) {
        none.add(type);
    }

    public void addAll(ComponentMatchType<?> type) {
        all.add(type);
    }
}
