package priv.kgame.lib.ecstest.component.remove.system;

import priv.kgame.lib.ecs.Entity;
import priv.kgame.lib.ecs.annotation.UpdateInGroup;
import priv.kgame.lib.ecs.extensions.system.EcsUpdateSystemTwo;
import priv.kgame.lib.ecstest.component.remove.component.ComponentRemove1;
import priv.kgame.lib.ecstest.component.remove.component.ComponentRemove3;
import priv.kgame.lib.ecstest.component.remove.group.SysGroupRemoveCompA;

@UpdateInGroup(SysGroupRemoveCompA.class)
public class SystemRemoveComp3 extends EcsUpdateSystemTwo<ComponentRemove1, ComponentRemove3> {

    @Override
    protected void update(Entity entity, ComponentRemove1 a1, ComponentRemove3 a3) {
        System.out.println(this.getClass().getSimpleName() +" update at: " + getWorld().getCurrentTime());
        a1.data += "a3";
    }
}
