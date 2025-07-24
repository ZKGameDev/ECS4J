package top.kgame.lib.ecstest.entity.remove.delay.entity;

import top.kgame.lib.ecs.EcsComponent;
import top.kgame.lib.ecs.extensions.entity.BaseEntityFactory;
import top.kgame.lib.ecs.extensions.entity.EntityFactoryAttribute;
import top.kgame.lib.ecstest.entity.remove.delay.component.EntityDelayRemove1;
import top.kgame.lib.ecstest.entity.remove.delay.component.EntityDelayRemove2;

import java.util.Collection;
import java.util.List;

@EntityFactoryAttribute
public class EntityDelayRemoveFactory12 extends BaseEntityFactory {

    @Override
    public int typeId() {
        return 12;
    }

    @Override
    protected Collection<EcsComponent> generateComponent() {
        return List.of(new EntityDelayRemove1(), new EntityDelayRemove2());
    }
} 