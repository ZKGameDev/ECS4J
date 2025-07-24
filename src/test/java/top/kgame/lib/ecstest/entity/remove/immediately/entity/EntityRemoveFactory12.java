package top.kgame.lib.ecstest.entity.remove.immediately.entity;

import top.kgame.lib.ecs.EcsComponent;
import top.kgame.lib.ecs.extensions.entity.BaseEntityFactory;
import top.kgame.lib.ecs.extensions.entity.EntityFactoryAttribute;
import top.kgame.lib.ecstest.entity.remove.immediately.component.EntityRemove1;
import top.kgame.lib.ecstest.entity.remove.immediately.component.EntityRemove2;

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