package org.kgame.lib.ecstest.entity.add.immediately.entity;

import org.kgame.lib.ecs.EcsComponent;
import org.kgame.lib.ecs.extensions.entity.BaseEntityFactory;
import org.kgame.lib.ecs.extensions.entity.EntityFactoryAttribute;
import org.kgame.lib.ecstest.entity.add.immediately.component.EntityAdd2;
import org.kgame.lib.ecstest.entity.add.immediately.component.EntityAdd3;

import java.util.Collection;
import java.util.List;

@EntityFactoryAttribute
public class EntityAddFactory23 extends BaseEntityFactory {

    @Override
    public int typeId() {
        return 23;
    }

    @Override
    protected Collection<EcsComponent> generateComponent() {
        return List.of(new EntityAdd2(), new EntityAdd3());
    }
} 