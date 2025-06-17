package priv.kgame.lib.ecs.component.base;

import priv.kgame.lib.ecs.component.EcsComponent;

public class DespawningComponent implements EcsComponent {
    private DespawningComponent(){};
    private static final DespawningComponent INSTANCE = new DespawningComponent();
    public static EcsComponent generate() {
        return INSTANCE;
    }
}