package org.kgame.lib.ecs.command;

import org.kgame.lib.ecs.EcsWorld;
import org.kgame.lib.ecs.Entity;

public class SystemCommandDestroyEntity implements EcsCommand {
    private final EcsWorld ecsWorld;
    private final Entity entity;
    public SystemCommandDestroyEntity(EcsWorld ecsWorld, Entity entity) {
        this.ecsWorld = ecsWorld;
        this.entity = entity;
    }

    @Override
    public void execute() {
        ecsWorld.requestDestroyEntity(entity);
    }
}
