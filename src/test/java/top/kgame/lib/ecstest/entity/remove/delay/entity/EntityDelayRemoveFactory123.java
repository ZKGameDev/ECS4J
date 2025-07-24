package top.kgame.lib.ecstest.entity.remove.delay.entity;

import top.kgame.lib.ecs.EcsComponent;
import top.kgame.lib.ecs.extensions.entity.BaseEntityFactory;
import top.kgame.lib.ecs.extensions.entity.EntityFactoryAttribute;
import top.kgame.lib.ecstest.entity.remove.delay.component.EntityDelayRemove1;
import top.kgame.lib.ecstest.entity.remove.delay.component.EntityDelayRemove2;
import top.kgame.lib.ecstest.entity.remove.delay.component.EntityDelayRemove3;

import java.util.Collection;
import java.util.List;

@EntityFactoryAttribute
public class EntityDelayRemoveFactory123 extends BaseEntityFactory {

    @Override
    public int typeId() {
        return 123;
    }

    @Override
    protected Collection<EcsComponent> generateComponent() {
        return List.of(new EntityDelayRemove1(), new EntityDelayRemove2(), new EntityDelayRemove3());
    }
} 