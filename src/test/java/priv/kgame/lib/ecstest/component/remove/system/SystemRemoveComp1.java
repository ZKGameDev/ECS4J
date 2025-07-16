package priv.kgame.lib.ecstest.component.remove.system;

import priv.kgame.lib.ecs.Entity;
import priv.kgame.lib.ecs.annotation.UpdateInGroup;
import priv.kgame.lib.ecs.extensions.system.EcsUpdateSystemOne;
import priv.kgame.lib.ecstest.component.remove.component.ComponentRemove1;
import priv.kgame.lib.ecstest.component.remove.group.SysGroupRemoveCompA;

@UpdateInGroup(SysGroupRemoveCompA.class)
public class SystemRemoveComp1 extends EcsUpdateSystemOne<ComponentRemove1> {

    @Override
    protected void update(Entity entity, ComponentRemove1 component) {
        System.out.println(this.getClass().getSimpleName() +" update at: " + getWorld().getCurrentTime());
        component.data += "a1";
    }
}
