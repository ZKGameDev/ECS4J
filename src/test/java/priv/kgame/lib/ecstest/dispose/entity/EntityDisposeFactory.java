package priv.kgame.lib.ecstest.dispose.entity;

import priv.kgame.lib.ecs.EcsWorld;
import priv.kgame.lib.ecs.component.EcsComponent;
import priv.kgame.lib.ecs.entity.BaseEntityFactory;
import priv.kgame.lib.ecs.entity.Entity;
import priv.kgame.lib.ecs.entity.EntityFactory;
import priv.kgame.lib.ecs.entity.EntityFactoryAttribute;
import priv.kgame.lib.ecstest.dispose.component.ComponentDispose1;
import priv.kgame.lib.ecstest.dispose.component.ComponentDispose2;
import priv.kgame.lib.ecstest.dispose.component.ComponentDispose3;

import java.util.Collection;
import java.util.List;

@EntityFactoryAttribute
public class EntityDisposeFactory extends BaseEntityFactory {

    @Override
    public int typeId() {
        return 2;
    }

    @Override
    protected Collection<EcsComponent> generateComponent() {
        return List.of(new ComponentDispose1(), new ComponentDispose2(), new ComponentDispose3());
    }
}