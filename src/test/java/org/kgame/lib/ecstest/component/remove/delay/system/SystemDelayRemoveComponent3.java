package org.kgame.lib.ecstest.component.remove.delay.system;

import org.kgame.lib.ecs.Entity;
import org.kgame.lib.ecs.annotation.UpdateInGroup;
import org.kgame.lib.ecs.extensions.system.EcsUpdateSystemTwo;
import org.kgame.lib.ecstest.component.remove.delay.component.ComponentDelayRemove1;
import org.kgame.lib.ecstest.component.remove.delay.component.ComponentDelayRemove3;
import org.kgame.lib.ecstest.component.remove.delay.group.SysGroupDelayRemoveComponentB;

@UpdateInGroup(SysGroupDelayRemoveComponentB.class)
public class SystemDelayRemoveComponent3 extends EcsUpdateSystemTwo<ComponentDelayRemove1, ComponentDelayRemove3> {

    @Override
    protected void update(Entity entity, ComponentDelayRemove1 r1, ComponentDelayRemove3 r3) {
        System.out.println(this.getClass().getSimpleName() +" update at: " + getWorld().getCurrentTime());
        r1.data += "r3";
    }
} 