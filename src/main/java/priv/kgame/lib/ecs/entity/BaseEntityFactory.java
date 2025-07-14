package priv.kgame.lib.ecs.entity;

import priv.kgame.lib.ecs.component.EcsComponent;

import java.util.Collection;

public abstract class BaseEntityFactory implements EntityFactory{
    @Override
    public Entity create(EcsEntityManager ecsEntityManager) {
        return ecsEntityManager.createEntityInstance(typeId(), generateComponent());
    }

    protected abstract Collection<EcsComponent> generateComponent();
}