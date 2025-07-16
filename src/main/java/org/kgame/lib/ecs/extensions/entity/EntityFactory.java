package org.kgame.lib.ecs.extensions.entity;

import org.kgame.lib.ecs.core.EcsEntityManager;
import org.kgame.lib.ecs.Entity;

public interface EntityFactory {
    Entity create(EcsEntityManager ecsEntityManager);
    int typeId();
}
