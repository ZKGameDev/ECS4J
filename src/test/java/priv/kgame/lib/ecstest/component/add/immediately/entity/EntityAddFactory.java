package priv.kgame.lib.ecstest.component.add.immediately.entity;

import priv.kgame.lib.ecs.EcsComponent;
import priv.kgame.lib.ecs.extensions.entity.BaseEntityFactory;
import priv.kgame.lib.ecs.extensions.entity.EntityFactoryAttribute;
import priv.kgame.lib.ecstest.component.add.immediately.component.ComponentAdd1;
import priv.kgame.lib.ecstest.component.add.immediately.component.ComponentAdd2;

import java.util.Collection;
import java.util.List;

@EntityFactoryAttribute
public class EntityAddFactory extends BaseEntityFactory {

    @Override
    public int typeId() {
        return 1;
    }

    @Override
    protected Collection<EcsComponent> generateComponent() {
        return List.of(new ComponentAdd1(), new ComponentAdd2());
    }
}
