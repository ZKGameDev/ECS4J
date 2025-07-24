package top.kgame.lib.ecstest.component.remove.immediately.system;

import top.kgame.lib.ecs.Entity;
import top.kgame.lib.ecs.annotation.UpdateInGroup;
import top.kgame.lib.ecs.extensions.system.EcsUpdateSystemTwo;
import top.kgame.lib.ecstest.component.remove.immediately.component.ComponentRemove1;
import top.kgame.lib.ecstest.component.remove.immediately.component.ComponentRemove3;
import top.kgame.lib.ecstest.component.remove.immediately.group.SysGroupRemoveCompA;

@UpdateInGroup(SysGroupRemoveCompA.class)
public class SystemRemoveComp3 extends EcsUpdateSystemTwo<ComponentRemove1, ComponentRemove3> {

    @Override
    protected void update(Entity entity, ComponentRemove1 a1, ComponentRemove3 a3) {
        System.out.println(this.getClass().getSimpleName() +" update at: " + getWorld().getCurrentTime());
        a1.data += "a3";
    }
}
