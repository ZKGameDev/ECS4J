package top.kgame.lib.ecstest.component.remove.immediately.entity;

import top.kgame.lib.ecs.EcsComponent;
import top.kgame.lib.ecs.extensions.entity.BaseEntityFactory;
import top.kgame.lib.ecs.extensions.entity.EntityFactoryAttribute;
import top.kgame.lib.ecstest.component.remove.immediately.component.ComponentRemove1;
import top.kgame.lib.ecstest.component.remove.immediately.component.ComponentRemove2;
import top.kgame.lib.ecstest.component.remove.immediately.component.ComponentRemove3;

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
