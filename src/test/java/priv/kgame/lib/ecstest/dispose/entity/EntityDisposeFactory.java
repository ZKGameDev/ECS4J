package priv.kgame.lib.ecstest.dispose.entity;

import priv.kgame.lib.ecs.EcsWorld;
import priv.kgame.lib.ecs.entity.Entity;
import priv.kgame.lib.ecs.entity.EntityFactory;
import priv.kgame.lib.ecs.entity.EntityFactoryAttribute;
import priv.kgame.lib.ecstest.dispose.component.ComponentDispose1;
import priv.kgame.lib.ecstest.dispose.component.ComponentDispose2;
import priv.kgame.lib.ecstest.dispose.component.ComponentDispose3;

@EntityFactoryAttribute
public class EntityDisposeFactory implements EntityFactory {
    @Override
    public Entity create(EcsWorld ecsWorld) {
        Entity entity = ecsWorld.createEntity(typeId());
        ecsWorld.addComponent(entity, new ComponentDispose1());
        ecsWorld.addComponent(entity, new ComponentDispose2());
        ecsWorld.addComponent(entity, new ComponentDispose3());
        return entity;
    }

    @Override
    public int typeId() {
        return 2;
    }
} 