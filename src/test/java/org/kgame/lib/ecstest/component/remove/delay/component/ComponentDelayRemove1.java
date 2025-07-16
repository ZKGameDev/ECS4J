package org.kgame.lib.ecstest.component.remove.delay.component;

import org.kgame.lib.ecs.EcsComponent;
import org.kgame.lib.ecs.command.EcsCommand;
import org.kgame.lib.ecs.command.EcsCommandScope;

public class ComponentDelayRemove1 implements EcsComponent {
    public String data = "R1";

    public EcsCommand command = null;
    public EcsCommandScope level = null;
} 