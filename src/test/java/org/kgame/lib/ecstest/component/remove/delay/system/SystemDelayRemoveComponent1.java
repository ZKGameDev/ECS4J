package org.kgame.lib.ecstest.component.remove.delay.system;

import org.kgame.lib.ecs.Entity;
import org.kgame.lib.ecs.annotation.UpdateInGroup;
import org.kgame.lib.ecs.extensions.system.EcsUpdateSystemOne;
import org.kgame.lib.ecstest.component.remove.delay.component.ComponentDelayRemove1;
import org.kgame.lib.ecstest.component.remove.delay.group.SysGroupDelayRemoveComponentA;

@UpdateInGroup(SysGroupDelayRemoveComponentA.class)
public class SystemDelayRemoveComponent1 extends EcsUpdateSystemOne<ComponentDelayRemove1> {

    @Override
    protected void update(Entity entity, ComponentDelayRemove1 component) {
        System.out.println(this.getClass().getSimpleName() +" update at: " + getWorld().getCurrentTime());
        component.data += "r1";

        if (component.command != null) {
            addDelayCommand(component.command, component.level);
            component.command = null;
        }
    }
} 