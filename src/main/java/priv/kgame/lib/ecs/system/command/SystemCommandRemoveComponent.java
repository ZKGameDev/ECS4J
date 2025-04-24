package priv.kgame.lib.ecs.system.command;

import priv.kgame.lib.ecs.EcsWorld;
import priv.kgame.lib.ecs.component.EcsComponent;
import priv.kgame.lib.ecs.entity.Entity;

public class SystemCommandRemoveComponent implements SystemCommand{
    private final EcsWorld ecsWorld;
    private final Entity entity;
    private final EcsComponent component;

    public SystemCommandRemoveComponent(EcsWorld ecsWorld, Entity entity, EcsComponent component) {
        this.ecsWorld = ecsWorld;
        this.entity = entity;
        this.component = component;
    }

    @Override
    public SystemCommandType getType() {
        return SystemCommandType.RemoveComponent;
    }

    @Override
    public void execute() {
        ecsWorld.removeComponent(entity, component);
    }
}
