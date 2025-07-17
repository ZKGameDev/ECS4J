package org.kgame.lib.ecstest.entity.remove.delay.entity;

import org.kgame.lib.ecs.EcsComponent;
import org.kgame.lib.ecs.extensions.entity.BaseEntityFactory;
import org.kgame.lib.ecs.extensions.entity.EntityFactoryAttribute;
import org.kgame.lib.ecstest.entity.remove.delay.component.EntityDelayRemove1;
import org.kgame.lib.ecstest.entity.remove.delay.component.EntityDelayRemove2;
import org.kgame.lib.ecstest.entity.remove.delay.component.EntityDelayRemove3;

import java.util.Collection;
import java.util.List;

@EntityFactoryAttribute
public class EntityDelayRemoveFactory1 extends BaseEntityFactory {

    @Override
    public int typeId() {
        return 1;
    }

    @Override
    protected Collection<EcsComponent> generateComponent() {
        return List.of(new EntityDelayRemove1());
    }
} 