package org.kgame.lib.ecstest.component.remove.delay.entity;

import org.kgame.lib.ecs.EcsComponent;
import org.kgame.lib.ecs.extensions.entity.BaseEntityFactory;
import org.kgame.lib.ecs.extensions.entity.EntityFactoryAttribute;
import org.kgame.lib.ecstest.component.remove.delay.component.ComponentDelayRemove1;
import org.kgame.lib.ecstest.component.remove.delay.component.ComponentDelayRemove2;
import org.kgame.lib.ecstest.component.remove.delay.component.ComponentDelayRemove3;

import java.util.Collection;
import java.util.List;

@EntityFactoryAttribute
public class EntityDelayRemoveFactory extends BaseEntityFactory {

    @Override
    public int typeId() {
        return 1;
    }

    @Override
    protected Collection<EcsComponent> generateComponent() {
        return List.of(new ComponentDelayRemove1(), new ComponentDelayRemove2(), new ComponentDelayRemove3());
    }
} 