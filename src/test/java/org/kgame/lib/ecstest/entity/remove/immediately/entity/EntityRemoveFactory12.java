package org.kgame.lib.ecstest.entity.remove.immediately.entity;

import org.kgame.lib.ecs.EcsComponent;
import org.kgame.lib.ecs.extensions.entity.BaseEntityFactory;
import org.kgame.lib.ecs.extensions.entity.EntityFactoryAttribute;
import org.kgame.lib.ecstest.entity.remove.immediately.component.EntityRemove1;
import org.kgame.lib.ecstest.entity.remove.immediately.component.EntityRemove2;
import org.kgame.lib.ecstest.entity.remove.immediately.component.EntityRemove3;

import java.util.Collection;
import java.util.List;

@EntityFactoryAttribute
public class EntityRemoveFactory12 extends BaseEntityFactory {

    @Override
    public int typeId() {
        return 12;
    }

    @Override
    protected Collection<EcsComponent> generateComponent() {
        return List.of(new EntityRemove1(), new EntityRemove2());
    }
} 