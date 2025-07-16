package org.kgame.lib.ecstest.component.remove.immediately.system;

import org.kgame.lib.ecs.Entity;
import org.kgame.lib.ecs.annotation.UpdateInGroup;
import org.kgame.lib.ecs.extensions.system.EcsUpdateSystemOne;
import org.kgame.lib.ecstest.component.remove.immediately.component.ComponentRemove1;
import org.kgame.lib.ecstest.component.remove.immediately.component.ComponentRemove3;
import org.kgame.lib.ecstest.component.remove.immediately.group.SysGroupRemoveCompA;

@UpdateInGroup(SysGroupRemoveCompA.class)
public class SystemRemoveComp2 extends EcsUpdateSystemOne<ComponentRemove3> {

    @Override
    protected void update(Entity entity, ComponentRemove3 component) {
        System.out.println(this.getClass().getSimpleName() +" update at: " + getWorld().getCurrentTime());
        ComponentRemove1 a1 = entity.getComponent(ComponentRemove1.class);
        a1.data += "a2";
    }
}
