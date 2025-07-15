package priv.kgame.lib.ecs.extensions.component;

import priv.kgame.lib.ecs.EcsComponent;

public class InitializedComponent implements EcsComponent {
    private InitializedComponent(){}
    private static final InitializedComponent INSTANCE = new InitializedComponent();
    public static InitializedComponent generate() {
        return INSTANCE;
    }
}