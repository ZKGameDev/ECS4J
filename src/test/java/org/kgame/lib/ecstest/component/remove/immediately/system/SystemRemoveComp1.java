package org.kgame.lib.ecstest.component.remove.immediately.system;

import org.kgame.lib.ecs.Entity;
import org.kgame.lib.ecs.annotation.UpdateInGroup;
import org.kgame.lib.ecs.extensions.system.EcsUpdateSystemOne;
import org.kgame.lib.ecstest.component.remove.immediately.component.ComponentRemove1;
import org.kgame.lib.ecstest.component.remove.immediately.group.SysGroupRemoveCompA;

@UpdateInGroup(SysGroupRemoveCompA.class)
public class SystemRemoveComp1 extends EcsUpdateSystemOne<ComponentRemove1> {

    @Override
    protected void update(Entity entity, ComponentRemove1 component) {
        System.out.println(this.getClass().getSimpleName() +" update at: " + getWorld().getCurrentTime());
        component.data += "a1";
    }
}
