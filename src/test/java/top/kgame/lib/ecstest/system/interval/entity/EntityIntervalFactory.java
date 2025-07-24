package top.kgame.lib.ecstest.system.interval.entity;

import top.kgame.lib.ecs.EcsComponent;
import top.kgame.lib.ecs.extensions.entity.BaseEntityFactory;
import top.kgame.lib.ecstest.system.interval.component.ComponentInterval1;
import top.kgame.lib.ecstest.system.interval.component.ComponentInterval2;
import top.kgame.lib.ecs.extensions.entity.EntityFactoryAttribute;
import top.kgame.lib.ecstest.system.interval.component.ComponentInterval3;

import java.util.Collection;
import java.util.List;

@EntityFactoryAttribute
public class EntityIntervalFactory extends BaseEntityFactory {

    @Override
    public int typeId() {
        return 2;
    }

    @Override
    protected Collection<EcsComponent> generateComponent() {
        return List.of(new ComponentInterval1(), new ComponentInterval2(), new ComponentInterval3());
    }
}