package priv.kgame.lib.ecs.test.order.def.entity;

import priv.kgame.lib.ecs.EcsWorld;
import priv.kgame.lib.ecs.entity.Entity;
import priv.kgame.lib.ecs.entity.EntityFactory;
import priv.kgame.lib.ecs.entity.EntityFactoryAttribute;
import priv.kgame.lib.ecs.test.order.def.component.*;

@EntityFactoryAttribute
public class EntityAFactory implements EntityFactory {
    @Override
    public Entity create(EcsWorld ecsWorld) {
        Entity entity = ecsWorld.createEntity(typeId());
        ecsWorld.addComponent(entity, new ComponentA1());
        ecsWorld.addComponent(entity, new ComponentA2());
        ecsWorld.addComponent(entity, new ComponentA3());
        ecsWorld.addComponent(entity, new ComponentA4());
        ecsWorld.addComponent(entity, new ComponentA5());
        return entity;
    }

    @Override
    public int typeId() {
        return 1;
    }
}
