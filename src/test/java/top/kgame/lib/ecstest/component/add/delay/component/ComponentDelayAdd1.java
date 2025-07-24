package top.kgame.lib.ecstest.component.add.delay.component;

import top.kgame.lib.ecs.EcsComponent;
import top.kgame.lib.ecs.command.EcsCommand;
import top.kgame.lib.ecs.command.EcsCommandScope;

public class ComponentDelayAdd1 implements EcsComponent {
    public String data = "A1";

    public EcsCommand command = null;
    public EcsCommandScope level = null;
}
