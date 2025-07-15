package priv.kgame.lib.ecstest.system.interval.entity;

import priv.kgame.lib.ecs.EcsComponent;
import priv.kgame.lib.ecs.extensions.entity.BaseEntityFactory;
import priv.kgame.lib.ecstest.system.interval.component.ComponentInterval1;
import priv.kgame.lib.ecstest.system.interval.component.ComponentInterval2;
import priv.kgame.lib.ecs.extensions.entity.EntityFactoryAttribute;
import priv.kgame.lib.ecstest.system.interval.component.ComponentInterval3;

import java.util.Collection;
import java.util.List;

@EntityFactoryAttribute
public class EntityIntervalFactory extends BaseEntityFactory {

    @Override
    public int typeId() {
        return 2;
    }

    @Override
    protected Collection<EcsComponent> generateComponent() {
        return List.of(new ComponentInterval1(), new ComponentInterval2(), new ComponentInterval3());
    }
}