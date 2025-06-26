package priv.kgame.lib.ecs.test.interval.system;

import priv.kgame.lib.ecs.entity.Entity;
import priv.kgame.lib.ecs.system.annotation.UpdateInGroup;
import priv.kgame.lib.ecs.system.base.EcsUpdateSystemOne;
import priv.kgame.lib.ecs.test.interval.component.ComponentInterval1;
import priv.kgame.lib.ecs.test.interval.group.SysGroupInterval;

@UpdateInGroup(SysGroupInterval.class)
public class SystemInterval1 extends EcsUpdateSystemOne<ComponentInterval1> {

    @Override
    protected void update(Entity entity, ComponentInterval1 component) {
        System.out.println(this.getClass().getSimpleName() +" update at: " + System.currentTimeMillis());
    }
} 