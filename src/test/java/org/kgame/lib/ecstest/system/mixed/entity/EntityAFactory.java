package org.kgame.lib.ecstest.system.mixed.entity;

import org.kgame.lib.ecs.EcsComponent;
import org.kgame.lib.ecs.extensions.entity.BaseEntityFactory;
import org.kgame.lib.ecs.extensions.entity.EntityFactoryAttribute;
import org.kgame.lib.ecstest.system.mixed.component.ComponentA1;
import org.kgame.lib.ecstest.system.mixed.component.ComponentA2;

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
        return List.of(new ComponentA1(), new ComponentA2());
    }
}
