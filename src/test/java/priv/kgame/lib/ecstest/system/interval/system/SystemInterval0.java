package priv.kgame.lib.ecstest.system.interval.system;

import priv.kgame.lib.ecs.Entity;
import priv.kgame.lib.ecs.annotation.UpdateInGroup;
import priv.kgame.lib.ecs.extensions.system.EcsUpdateSystemThree;
import priv.kgame.lib.ecstest.system.interval.component.ComponentInterval1;
import priv.kgame.lib.ecstest.system.interval.component.ComponentInterval2;
import priv.kgame.lib.ecstest.system.interval.component.ComponentInterval3;
import priv.kgame.lib.ecstest.system.interval.group.SysGroupInterval;

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