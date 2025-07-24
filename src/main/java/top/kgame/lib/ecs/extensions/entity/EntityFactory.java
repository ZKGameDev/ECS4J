package top.kgame.lib.ecs.extensions.entity;

import top.kgame.lib.ecs.core.EcsEntityManager;
import top.kgame.lib.ecs.Entity;

public interface EntityFactory {
    Entity create(EcsEntityManager ecsEntityManager);
    int typeId();
}
