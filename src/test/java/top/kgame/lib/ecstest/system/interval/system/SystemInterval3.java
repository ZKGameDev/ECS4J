package top.kgame.lib.ecstest.system.interval.system;

import top.kgame.lib.ecs.Entity;
import top.kgame.lib.ecs.annotation.UpdateInGroup;
import top.kgame.lib.ecs.annotation.UpdateIntervalTime;
import top.kgame.lib.ecs.extensions.system.EcsUpdateSystemThree;
import top.kgame.lib.ecstest.system.interval.component.ComponentInterval1;
import top.kgame.lib.ecstest.system.interval.component.ComponentInterval2;
import top.kgame.lib.ecstest.system.interval.component.ComponentInterval3;
import top.kgame.lib.ecstest.system.interval.group.SysGroupInterval;

@UpdateInGroup(SysGroupInterval.class)
@UpdateIntervalTime(interval=0.066f)
public class SystemInterval3 extends EcsUpdateSystemThree<ComponentInterval1, ComponentInterval2, ComponentInterval3> {

    @Override
    protected void update(Entity entity, ComponentInterval1 interval1, ComponentInterval2 interval2, ComponentInterval3 interval3) {
        System.out.println(this.getClass().getSimpleName() +" update at: " + getWorld().getCurrentTime());
        interval3.i3 = interval1.i1 + interval2.i2;
    }
} 