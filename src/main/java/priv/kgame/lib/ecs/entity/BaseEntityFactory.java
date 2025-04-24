package priv.kgame.lib.ecs.entity;

import priv.kgame.lib.ecs.EcsWorld;

public abstract class BaseEntityFactory implements EntityFactory{
    @Override
    public Entity create(EcsWorld ecsWorld) {
        Entity entity = ecsWorld.createEntity(typeId());
        onCreate(ecsWorld, entity);
        return entity;
    }

    protected abstract void onCreate(EcsWorld ecsWorld, Entity entity);
}
