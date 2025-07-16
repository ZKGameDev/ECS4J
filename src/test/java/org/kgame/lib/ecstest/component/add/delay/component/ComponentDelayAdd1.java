package org.kgame.lib.ecstest.component.add.delay.component;

import org.kgame.lib.ecs.EcsComponent;
import org.kgame.lib.ecs.command.EcsCommand;
import org.kgame.lib.ecs.command.EcsCommandScope;

public class ComponentDelayAdd1 implements EcsComponent {
    public String data = "A1";

    public EcsCommand command = null;
    public EcsCommandScope level = null;
}
