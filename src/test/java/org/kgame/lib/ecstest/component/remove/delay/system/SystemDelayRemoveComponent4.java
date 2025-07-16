package org.kgame.lib.ecstest.component.remove.delay.system;

import org.kgame.lib.ecs.Entity;
import org.kgame.lib.ecs.annotation.UpdateInGroup;
import org.kgame.lib.ecs.extensions.system.EcsUpdateSystemOne;
import org.kgame.lib.ecstest.component.remove.delay.component.ComponentDelayRemove1;
import org.kgame.lib.ecstest.component.remove.delay.component.ComponentDelayRemove2;
import org.kgame.lib.ecstest.component.remove.delay.group.SysGroupDelayRemoveComponentB;

@UpdateInGroup(SysGroupDelayRemoveComponentB.class)
public class SystemDelayRemoveComponent4 extends EcsUpdateSystemOne<ComponentDelayRemove2> {

    @Override
    protected void update(Entity entity, ComponentDelayRemove2 component) {
        System.out.println(this.getClass().getSimpleName() +" update at: " + getWorld().getCurrentTime());
        ComponentDelayRemove1 componentDelayRemove1 = entity.getComponent(ComponentDelayRemove1.class);
        component.data = componentDelayRemove1.data;
        componentDelayRemove1.data = "";
    }
}
