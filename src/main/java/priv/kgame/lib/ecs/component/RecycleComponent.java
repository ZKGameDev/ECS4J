package priv.kgame.lib.ecs.component;

public interface RecycleComponent<T> extends EcsComponent {
    void clear();
}
