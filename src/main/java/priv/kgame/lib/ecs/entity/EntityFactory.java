package priv.kgame.lib.ecs.entity;

import priv.kgame.lib.ecs.EcsWorld;

public interface EntityFactory {
    Entity create(EcsWorld ecsWorld);
    int typeId();
}
