package priv.kgame.lib.ecs.test.b.entity;

import priv.kgame.lib.ecs.EcsWorld;
import priv.kgame.lib.ecs.test.b.component.ComponentB1;
import priv.kgame.lib.ecs.test.b.component.ComponentB2;
import priv.kgame.lib.ecs.test.b.component.ComponentB3;
import priv.kgame.lib.ecs.entity.Entity;
import priv.kgame.lib.ecs.entity.EntityFactory;
import priv.kgame.lib.ecs.entity.EntityFactoryAttribute;

@EntityFactoryAttribute
public class EntityBFactory implements EntityFactory {
    @Override
    public Entity create(EcsWorld ecsWorld) {
        Entity entity = ecsWorld.createEntity(typeId());
        ecsWorld.addComponent(entity, new ComponentB1());
        ecsWorld.addComponent(entity, new ComponentB2());
        ecsWorld.addComponent(entity, new ComponentB3());
        return entity;
    }

    @Override
    public int typeId() {
        return 2;
    }
} 