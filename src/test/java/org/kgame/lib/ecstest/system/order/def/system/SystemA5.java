package org.kgame.lib.ecstest.system.order.def.system;

import org.kgame.lib.ecs.Entity;
import org.kgame.lib.ecs.annotation.UpdateInGroup;
import org.kgame.lib.ecs.extensions.system.EcsUpdateSystemOne;
import org.kgame.lib.ecstest.system.order.def.component.ComponentA1;
import org.kgame.lib.ecstest.system.order.def.component.ComponentA2;
import org.kgame.lib.ecstest.system.order.def.group.SysGroupDefOrderA;

@UpdateInGroup(SysGroupDefOrderA.class)
public class SystemA5 extends EcsUpdateSystemOne<ComponentA2> {

    @Override
    protected void update(Entity entity, ComponentA2 component) {
        System.out.println(this.getClass().getSimpleName() +" update at: " + getWorld().getCurrentTime());
        ComponentA1 componentA1 = entity.getComponent(ComponentA1.class);
        component.data = componentA1.data;
        componentA1.data = "";
    }
}
