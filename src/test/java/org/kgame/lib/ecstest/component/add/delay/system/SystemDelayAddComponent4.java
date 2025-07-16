package org.kgame.lib.ecstest.component.add.delay.system;

import org.kgame.lib.ecs.Entity;
import org.kgame.lib.ecs.annotation.UpdateInGroup;
import org.kgame.lib.ecs.extensions.system.EcsUpdateSystemOne;
import org.kgame.lib.ecstest.component.add.delay.component.ComponentDelayAdd1;
import org.kgame.lib.ecstest.component.add.delay.component.ComponentDelayAdd2;
import org.kgame.lib.ecstest.component.add.delay.group.SysGroupDelayAddComponentB;

@UpdateInGroup(SysGroupDelayAddComponentB.class)
public class SystemDelayAddComponent4 extends EcsUpdateSystemOne<ComponentDelayAdd2> {

    @Override
    protected void update(Entity entity, ComponentDelayAdd2 component) {
        System.out.println(this.getClass().getSimpleName() +" update at: " + getWorld().getCurrentTime());
        ComponentDelayAdd1 componentDelayAdd1 = entity.getComponent(ComponentDelayAdd1.class);
        component.data = componentDelayAdd1.data;
        componentDelayAdd1.data = "";
    }
}
