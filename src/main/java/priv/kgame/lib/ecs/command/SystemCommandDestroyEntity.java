package priv.kgame.lib.ecs.command;

import priv.kgame.lib.ecs.EcsWorld;
import priv.kgame.lib.ecs.Entity;

public class SystemCommandDestroyEntity implements EcsCommand {
    private final EcsWorld ecsWorld;
    private final Entity entity;
    public SystemCommandDestroyEntity(EcsWorld ecsWorld, Entity entity) {
        this.ecsWorld = ecsWorld;
        this.entity = entity;
    }

    @Override
    public SystemCommandType getType() {
        return SystemCommandType.DestroyEntity;
    }

    @Override
    public void execute() {
        ecsWorld.requestDestroyEntity(entity);
    }
}
