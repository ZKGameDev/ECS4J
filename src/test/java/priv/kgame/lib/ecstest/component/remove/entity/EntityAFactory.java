package priv.kgame.lib.ecstest.component.remove.entity;

import priv.kgame.lib.ecs.EcsComponent;
import priv.kgame.lib.ecs.extensions.entity.BaseEntityFactory;
import priv.kgame.lib.ecs.extensions.entity.EntityFactoryAttribute;
import priv.kgame.lib.ecstest.component.remove.component.ComponentA1;
import priv.kgame.lib.ecstest.component.remove.component.ComponentA2;
import priv.kgame.lib.ecstest.component.remove.component.ComponentA3;

import java.util.Collection;
import java.util.List;

@EntityFactoryAttribute
public class EntityAFactory extends BaseEntityFactory {

    @Override
    public int typeId() {
        return 1;
    }

    @Override
    protected Collection<EcsComponent> generateComponent() {
        return List.of(new ComponentA1(), new ComponentA2(), new ComponentA3());
    }
}
