package priv.kgame.lib.ecstest.system.order.custom.entity;

import priv.kgame.lib.ecs.extensions.entity.BaseEntityFactory;
import priv.kgame.lib.ecs.extensions.entity.EntityFactoryAttribute;
import priv.kgame.lib.ecs.EcsComponent;
import priv.kgame.lib.ecstest.system.order.custom.component.ComponentA1;
import priv.kgame.lib.ecstest.system.order.custom.component.ComponentA2;

import java.util.Collection;
import java.util.List;

@EntityFactoryAttribute
public class EntityAFactory extends BaseEntityFactory {

    @Override
    protected Collection<EcsComponent> generateComponent() {
        return List.of(new ComponentA1(), new ComponentA2());
    }

    @Override
    public int typeId() {
        return 1;
    }
}
