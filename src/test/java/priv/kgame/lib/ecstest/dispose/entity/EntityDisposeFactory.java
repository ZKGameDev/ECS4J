package priv.kgame.lib.ecstest.dispose.entity;

import priv.kgame.lib.ecs.EcsComponent;
import priv.kgame.lib.ecs.extensions.entity.BaseEntityFactory;
import priv.kgame.lib.ecs.extensions.entity.EntityFactoryAttribute;
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