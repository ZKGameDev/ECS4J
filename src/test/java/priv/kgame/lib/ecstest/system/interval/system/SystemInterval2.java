package priv.kgame.lib.ecstest.system.interval.system;

import priv.kgame.lib.ecs.Entity;
import priv.kgame.lib.ecs.annotation.UpdateInGroup;
import priv.kgame.lib.ecs.annotation.UpdateIntervalTime;
import priv.kgame.lib.ecs.extensions.system.EcsUpdateSystemOne;
import priv.kgame.lib.ecstest.system.interval.component.ComponentInterval2;
import priv.kgame.lib.ecstest.system.interval.group.SysGroupInterval;

@UpdateInGroup(SysGroupInterval.class)
@UpdateIntervalTime(interval=0.033f)
public class SystemInterval2 extends EcsUpdateSystemOne<ComponentInterval2> {

    @Override
    protected void update(Entity entity, ComponentInterval2 component) {
        System.out.println(this.getClass().getSimpleName() +" update at: " + getWorld().getCurrentTime());
        component.i2 = "i2";
    }
} 