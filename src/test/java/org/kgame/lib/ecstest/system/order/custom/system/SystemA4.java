package org.kgame.lib.ecstest.system.order.custom.system;

import org.kgame.lib.ecs.Entity;
import org.kgame.lib.ecs.annotation.UpdateAfterSystem;
import org.kgame.lib.ecs.annotation.UpdateBeforeSystem;
import org.kgame.lib.ecs.annotation.UpdateInGroup;
import org.kgame.lib.ecs.extensions.system.EcsUpdateSystemOne;
import org.kgame.lib.ecstest.system.order.custom.component.ComponentA1;
import org.kgame.lib.ecstest.system.order.custom.group.SysGroupCustomOrderA;

@UpdateInGroup(SysGroupCustomOrderA.class)
@UpdateAfterSystem(systemTypes = { SystemA2.class })
@UpdateBeforeSystem(systemTypes = { SystemA0.class })
public class SystemA4 extends EcsUpdateSystemOne<ComponentA1> {

    @Override
    protected void update(Entity entity, ComponentA1 component) {
        System.out.println(this.getClass().getSimpleName() +" update at: " + getWorld().getCurrentTime());
        component.data += "a4";
    }
}
