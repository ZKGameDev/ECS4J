package priv.kgame.lib.ecs.test.order.def.component;

import priv.kgame.lib.ecs.component.EcsComponent;

public class ComponentA1 implements EcsComponent {
    private String data = "A1";

    public String getData() {
        return data;
    }
}
