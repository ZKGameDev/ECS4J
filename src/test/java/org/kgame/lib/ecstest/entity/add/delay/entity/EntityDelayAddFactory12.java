package org.kgame.lib.ecstest.entity.add.delay.entity;

import org.kgame.lib.ecs.EcsComponent;
import org.kgame.lib.ecs.extensions.entity.BaseEntityFactory;
import org.kgame.lib.ecs.extensions.entity.EntityFactoryAttribute;
import org.kgame.lib.ecstest.entity.add.delay.component.EntityDelayAdd1;
import org.kgame.lib.ecstest.entity.add.delay.component.EntityDelayAdd2;

import java.util.Collection;
import java.util.List;

@EntityFactoryAttribute
public class EntityDelayAddFactory12 extends BaseEntityFactory {

    @Override
    public int typeId() {
        return 12;
    }

    @Override
    protected Collection<EcsComponent> generateComponent() {
        return List.of(new EntityDelayAdd1(), new EntityDelayAdd2());
    }
} 