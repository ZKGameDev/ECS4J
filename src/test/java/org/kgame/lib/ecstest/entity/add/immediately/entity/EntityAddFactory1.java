package org.kgame.lib.ecstest.entity.add.immediately.entity;

import org.kgame.lib.ecs.EcsComponent;
import org.kgame.lib.ecs.extensions.entity.BaseEntityFactory;
import org.kgame.lib.ecs.extensions.entity.EntityFactoryAttribute;
import org.kgame.lib.ecstest.entity.add.immediately.component.EntityAdd1;

import java.util.Collection;
import java.util.List;

@EntityFactoryAttribute
public class EntityAddFactory1 extends BaseEntityFactory {

    @Override
    public int typeId() {
        return 1;
    }

    @Override
    protected Collection<EcsComponent> generateComponent() {
        return List.of(new EntityAdd1());
    }
} 