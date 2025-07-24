package top.kgame.lib.ecstest.system.interval.system;

import top.kgame.lib.ecs.Entity;
import top.kgame.lib.ecs.annotation.UpdateInGroup;
import top.kgame.lib.ecs.extensions.system.EcsUpdateSystemThree;
import top.kgame.lib.ecstest.system.interval.component.ComponentInterval1;
import top.kgame.lib.ecstest.system.interval.component.ComponentInterval2;
import top.kgame.lib.ecstest.system.interval.component.ComponentInterval3;
import top.kgame.lib.ecstest.system.interval.group.SysGroupInterval;

@UpdateInGroup(SysGroupInterval.class)
public class SystemInterval0 extends EcsUpdateSystemThree<ComponentInterval1, ComponentInterval2, ComponentInterval3> {

    @Override
    protected void update(Entity entity, ComponentInterval1 component1, ComponentInterval2 component2, ComponentInterval3 component3) {
        System.out.println(this.getClass().getSimpleName() +" update at: " + getWorld().getCurrentTime());
        component1.i1 = "";
        component2.i2 = "";
        component3.i3 = "";
    }
}