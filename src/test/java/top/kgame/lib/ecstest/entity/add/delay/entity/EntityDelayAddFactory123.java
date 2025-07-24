package top.kgame.lib.ecstest.entity.add.delay.entity;

import top.kgame.lib.ecs.EcsComponent;
import top.kgame.lib.ecs.extensions.entity.BaseEntityFactory;
import top.kgame.lib.ecs.extensions.entity.EntityFactoryAttribute;
import top.kgame.lib.ecstest.entity.add.delay.component.EntityDelayAdd1;
import top.kgame.lib.ecstest.entity.add.delay.component.EntityDelayAdd2;
import top.kgame.lib.ecstest.entity.add.delay.component.EntityDelayAdd3;

import java.util.Collection;
import java.util.List;

@EntityFactoryAttribute
public class EntityDelayAddFactory123 extends BaseEntityFactory {

    @Override
    public int typeId() {
        return 123;
    }

    @Override
    protected Collection<EcsComponent> generateComponent() {
        return List.of(new EntityDelayAdd1(), new EntityDelayAdd2(), new EntityDelayAdd3());
    }
} 