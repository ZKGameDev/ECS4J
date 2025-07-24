package top.kgame.lib.ecstest.component.remove.delay.component;

import top.kgame.lib.ecs.EcsComponent;
import top.kgame.lib.ecs.command.EcsCommand;
import top.kgame.lib.ecs.command.EcsCommandScope;

public class ComponentDelayRemove1 implements EcsComponent {
    public String data = "R1";

    public EcsCommand command = null;
    public EcsCommandScope level = null;
} 