package priv.kgame.lib.ecs.component.base;

import priv.kgame.lib.ecs.component.EcsComponent;

public class InitializedComponent implements EcsComponent {
    private InitializedComponent(){}
    private static final InitializedComponent INSTANCE = new InitializedComponent();
    public static InitializedComponent generate() {
        return INSTANCE;
    }
}