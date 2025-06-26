package priv.kgame.lib.ecs.component.base;

import priv.kgame.lib.ecs.component.EcsComponent;

public class DestroyingComponent implements EcsComponent {
    private DestroyingComponent(){};
    private static final DestroyingComponent INSTANCE = new DestroyingComponent();
    public static EcsComponent generate() {
        return INSTANCE;
    }
}
