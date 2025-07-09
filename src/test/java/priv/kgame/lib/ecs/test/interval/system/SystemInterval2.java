package priv.kgame.lib.ecs.test.interval.system;

import priv.kgame.lib.ecs.entity.Entity;
import priv.kgame.lib.ecs.system.annotation.UpdateInGroup;
import priv.kgame.lib.ecs.system.annotation.UpdateIntervalTime;
import priv.kgame.lib.ecs.system.base.EcsUpdateSystemOne;
import priv.kgame.lib.ecs.test.interval.component.ComponentInterval2;
import priv.kgame.lib.ecs.test.interval.group.SysGroupInterval;

@UpdateInGroup(SysGroupInterval.class)
@UpdateIntervalTime(interval=0.033f)
public class SystemInterval2 extends EcsUpdateSystemOne<ComponentInterval2> {

    @Override
    protected void update(Entity entity, ComponentInterval2 component) {
        System.out.println(this.getClass().getSimpleName() +" update at: " + getWorld().getCurrentTime());
        component.i2 = "i2";
    }
} 