package priv.kgame.lib.ecs.system.command;

import priv.kgame.lib.ecs.EcsWorld;
import priv.kgame.lib.ecs.entity.Entity;

public class SystemCommandDestroyEntity implements SystemCommand{
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
        ecsWorld.destroyEntity(entity);
    }
}
