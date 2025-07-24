package top.kgame.lib.ecstest.entity.add.delay.entity;

import top.kgame.lib.ecs.EcsComponent;
import top.kgame.lib.ecs.extensions.entity.BaseEntityFactory;
import top.kgame.lib.ecs.extensions.entity.EntityFactoryAttribute;
import top.kgame.lib.ecstest.entity.add.delay.component.EntityDelayAdd1;

import java.util.Collection;
import java.util.List;

@EntityFactoryAttribute
public class EntityDelayAddFactory1 extends BaseEntityFactory {

    @Override
    public int typeId() {
        return 1;
    }

    @Override
    protected Collection<EcsComponent> generateComponent() {
        return List.of(new EntityDelayAdd1());
    }
} 