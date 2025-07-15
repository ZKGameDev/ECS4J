package priv.kgame.lib.ecs.extensions.entity;

import priv.kgame.lib.ecs.core.EcsEntityManager;
import priv.kgame.lib.ecs.Entity;

public interface EntityFactory {
    Entity create(EcsEntityManager ecsEntityManager);
    int typeId();
}
