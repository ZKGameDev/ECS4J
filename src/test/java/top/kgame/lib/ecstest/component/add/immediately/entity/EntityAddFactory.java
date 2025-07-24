package top.kgame.lib.ecstest.component.add.immediately.entity;

import top.kgame.lib.ecs.EcsComponent;
import top.kgame.lib.ecs.extensions.entity.BaseEntityFactory;
import top.kgame.lib.ecs.extensions.entity.EntityFactoryAttribute;
import top.kgame.lib.ecstest.component.add.immediately.component.ComponentAdd1;
import top.kgame.lib.ecstest.component.add.immediately.component.ComponentAdd2;

import java.util.Collection;
import java.util.List;

@EntityFactoryAttribute
public class EntityAddFactory extends BaseEntityFactory {

    @Override
    public int typeId() {
        return 1;
    }

    @Override
    protected Collection<EcsComponent> generateComponent() {
        return List.of(new ComponentAdd1(), new ComponentAdd2());
    }
}
