package org.kgame.lib.ecstest.component.remove.system;

import org.kgame.lib.ecs.Entity;
import org.kgame.lib.ecs.annotation.UpdateInGroup;
import org.kgame.lib.ecs.extensions.system.EcsUpdateSystemOne;
import org.kgame.lib.ecstest.component.remove.component.ComponentRemove1;
import org.kgame.lib.ecstest.component.remove.component.ComponentRemove2;
import org.kgame.lib.ecstest.component.remove.group.SysGroupRemoveCompA;

@UpdateInGroup(SysGroupRemoveCompA.class)
public class SystemRemoveComp4 extends EcsUpdateSystemOne<ComponentRemove2> {

    @Override
    protected void update(Entity entity, ComponentRemove2 component) {
        System.out.println(this.getClass().getSimpleName() +" update at: " + getWorld().getCurrentTime());
        ComponentRemove1 componentRemove1 = entity.getComponent(ComponentRemove1.class);
        component.data = componentRemove1.data;
        componentRemove1.data = "";
    }
}
