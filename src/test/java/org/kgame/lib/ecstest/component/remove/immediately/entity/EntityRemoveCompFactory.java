package org.kgame.lib.ecstest.component.remove.immediately.entity;

import org.kgame.lib.ecs.EcsComponent;
import org.kgame.lib.ecs.extensions.entity.BaseEntityFactory;
import org.kgame.lib.ecs.extensions.entity.EntityFactoryAttribute;
import org.kgame.lib.ecstest.component.remove.immediately.component.ComponentRemove1;
import org.kgame.lib.ecstest.component.remove.immediately.component.ComponentRemove2;
import org.kgame.lib.ecstest.component.remove.immediately.component.ComponentRemove3;

import java.util.Collection;
import java.util.List;

@EntityFactoryAttribute
public class EntityRemoveCompFactory extends BaseEntityFactory {

    @Override
    public int typeId() {
        return 1;
    }

    @Override
    protected Collection<EcsComponent> generateComponent() {
        return List.of(new ComponentRemove1(), new ComponentRemove2(), new ComponentRemove3());
    }
}
