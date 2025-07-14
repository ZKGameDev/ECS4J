package priv.kgame.lib.ecs.entity;

public interface EntityFactory {
    Entity create(EcsEntityManager ecsEntityManager);
    int typeId();
}
