package priv.kgame.lib.ecstest.component.add.delay.component;

import priv.kgame.lib.ecs.EcsComponent;
import priv.kgame.lib.ecs.command.EcsCommand;
import priv.kgame.lib.ecs.command.EcsCommandScope;

public class ComponentDelayAdd1 implements EcsComponent {
    public String data = "A1";

    public EcsCommand command = null;
    public EcsCommandScope level = null;
}
