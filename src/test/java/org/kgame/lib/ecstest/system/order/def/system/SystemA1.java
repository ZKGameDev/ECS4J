package org.kgame.lib.ecstest.system.order.def.system;

import org.kgame.lib.ecs.Entity;
import org.kgame.lib.ecs.annotation.UpdateInGroup;
import org.kgame.lib.ecs.extensions.system.EcsUpdateSystemOne;
import org.kgame.lib.ecstest.system.order.def.component.ComponentA1;
import org.kgame.lib.ecstest.system.order.def.group.SysGroupDefOrderA;

@UpdateInGroup(SysGroupDefOrderA.class)
public class SystemA1 extends EcsUpdateSystemOne<ComponentA1> {

    @Override
    protected void update(Entity entity, ComponentA1 component) {
        System.out.println(this.getClass().getSimpleName() +" update at: " + getWorld().getCurrentTime());
        component.data += "a1";
    }
}
