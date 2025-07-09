package priv.kgame.lib.ecs.test.interval.entity;

import priv.kgame.lib.ecs.EcsWorld;
import priv.kgame.lib.ecs.test.interval.component.ComponentInterval1;
import priv.kgame.lib.ecs.test.interval.component.ComponentInterval2;
import priv.kgame.lib.ecs.entity.Entity;
import priv.kgame.lib.ecs.entity.EntityFactory;
import priv.kgame.lib.ecs.entity.EntityFactoryAttribute;
import priv.kgame.lib.ecs.test.interval.component.ComponentInterval3;

@EntityFactoryAttribute
public class EntityIntervalFactory implements EntityFactory {
    @Override
    public Entity create(EcsWorld ecsWorld) {
        Entity entity = ecsWorld.createEntity(typeId());
        ecsWorld.addComponent(entity, new ComponentInterval1());
        ecsWorld.addComponent(entity, new ComponentInterval2());
        ecsWorld.addComponent(entity, new ComponentInterval3());
        return entity;
    }

    @Override
    public int typeId() {
        return 2;
    }
} 