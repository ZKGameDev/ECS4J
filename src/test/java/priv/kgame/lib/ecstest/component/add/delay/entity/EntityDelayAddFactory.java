package priv.kgame.lib.ecstest.component.add.delay.entity;

import priv.kgame.lib.ecs.EcsComponent;
import priv.kgame.lib.ecs.extensions.entity.BaseEntityFactory;
import priv.kgame.lib.ecs.extensions.entity.EntityFactoryAttribute;
import priv.kgame.lib.ecstest.component.add.delay.component.ComponentDelayAdd1;
import priv.kgame.lib.ecstest.component.add.delay.component.ComponentDelayAdd2;

import java.util.Collection;
import java.util.List;

@EntityFactoryAttribute
public class EntityDelayAddFactory extends BaseEntityFactory {

    @Override
    public int typeId() {
        return 1;
    }

    @Override
    protected Collection<EcsComponent> generateComponent() {
        return List.of(new ComponentDelayAdd1(), new ComponentDelayAdd2());
    }
}
