package priv.kgame.lib.ecstest.component.add.system;

import priv.kgame.lib.ecs.Entity;
import priv.kgame.lib.ecs.annotation.UpdateInGroup;
import priv.kgame.lib.ecs.extensions.system.EcsUpdateSystemTwo;
import priv.kgame.lib.ecstest.component.add.component.ComponentA1;
import priv.kgame.lib.ecstest.component.add.component.ComponentA3;
import priv.kgame.lib.ecstest.component.add.group.SysGroupAddCompA;

@UpdateInGroup(SysGroupAddCompA.class)
public class SystemA3 extends EcsUpdateSystemTwo<ComponentA1, ComponentA3> {

    @Override
    protected void update(Entity entity, ComponentA1 a1, ComponentA3 a3) {
        System.out.println(this.getClass().getSimpleName() +" update at: " + getWorld().getCurrentTime());
        a1.data += "a3";
    }
}
