package top.kgame.lib.ecstest.system.interval.system;

import top.kgame.lib.ecs.Entity;
import top.kgame.lib.ecs.annotation.UpdateInGroup;
import top.kgame.lib.ecs.annotation.UpdateIntervalTime;
import top.kgame.lib.ecs.extensions.system.EcsUpdateSystemOne;
import top.kgame.lib.ecstest.system.interval.component.ComponentInterval2;
import top.kgame.lib.ecstest.system.interval.group.SysGroupInterval;

@UpdateInGroup(SysGroupInterval.class)
@UpdateIntervalTime(interval=0.033f)
public class SystemInterval2 extends EcsUpdateSystemOne<ComponentInterval2> {

    @Override
    protected void update(Entity entity, ComponentInterval2 component) {
        System.out.println(this.getClass().getSimpleName() +" update at: " + getWorld().getCurrentTime());
        component.i2 = "i2";
    }
} 