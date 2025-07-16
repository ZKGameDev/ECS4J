package org.kgame.lib.ecstest.component.add.delay.system;

import org.kgame.lib.ecs.Entity;
import org.kgame.lib.ecs.annotation.UpdateInGroup;
import org.kgame.lib.ecs.extensions.system.EcsUpdateSystemOne;
import org.kgame.lib.ecstest.component.add.delay.component.ComponentDelayAdd1;
import org.kgame.lib.ecstest.component.add.delay.group.SysGroupDelayAddComponentA;

@UpdateInGroup(SysGroupDelayAddComponentA.class)
public class SystemDelayAddComponent1 extends EcsUpdateSystemOne<ComponentDelayAdd1> {

    @Override
    protected void update(Entity entity, ComponentDelayAdd1 component) {
        System.out.println(this.getClass().getSimpleName() +" update at: " + getWorld().getCurrentTime());
        component.data += "a1";

        if (component.command != null) {
            addDelayCommand(component.command, component.level);
            component.command = null;
        }
    }
}
