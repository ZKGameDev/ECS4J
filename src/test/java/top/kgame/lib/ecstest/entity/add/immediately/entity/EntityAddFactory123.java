package top.kgame.lib.ecstest.entity.add.immediately.entity;

import top.kgame.lib.ecs.EcsComponent;
import top.kgame.lib.ecs.extensions.entity.BaseEntityFactory;
import top.kgame.lib.ecs.extensions.entity.EntityFactoryAttribute;
import top.kgame.lib.ecstest.entity.add.immediately.component.EntityAdd1;
import top.kgame.lib.ecstest.entity.add.immediately.component.EntityAdd2;
import top.kgame.lib.ecstest.entity.add.immediately.component.EntityAdd3;

import java.util.Collection;
import java.util.List;

@EntityFactoryAttribute
public class EntityAddFactory123 extends BaseEntityFactory {

    @Override
    public int typeId() {
        return 123;
    }

    @Override
    protected Collection<EcsComponent> generateComponent() {
        return List.of(new EntityAdd1(), new EntityAdd2(), new EntityAdd3());
    }
} 