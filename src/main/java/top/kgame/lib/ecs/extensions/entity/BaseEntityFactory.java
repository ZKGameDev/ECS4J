package top.kgame.lib.ecs.extensions.entity;

import top.kgame.lib.ecs.EcsComponent;
import top.kgame.lib.ecs.core.EcsEntityManager;
import top.kgame.lib.ecs.Entity;

import java.util.Collection;

public abstract class BaseEntityFactory implements EntityFactory {
    @Override
    public Entity create(EcsEntityManager ecsEntityManager) {
        return ecsEntityManager.createEntityInstance(typeId(), generateComponent());
    }

    protected abstract Collection<EcsComponent> generateComponent();
}