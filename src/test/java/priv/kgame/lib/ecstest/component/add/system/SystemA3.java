package priv.kgame.lib.ecstest.component.add.system;

import priv.kgame.lib.ecs.entity.Entity;
import priv.kgame.lib.ecs.system.annotation.UpdateAfterSystem;
import priv.kgame.lib.ecs.system.annotation.UpdateBeforeSystem;
import priv.kgame.lib.ecs.system.annotation.UpdateInGroup;
import priv.kgame.lib.ecs.system.base.EcsUpdateSystemOne;
import priv.kgame.lib.ecs.system.base.EcsUpdateSystemTwo;
import priv.kgame.lib.ecstest.component.add.component.ComponentA1;
import priv.kgame.lib.ecstest.component.add.component.ComponentA3;
import priv.kgame.lib.ecstest.component.add.group.SysGroupA;

@UpdateInGroup(SysGroupA.class)
public class SystemA3 extends EcsUpdateSystemTwo<ComponentA1, ComponentA3> {

    @Override
    protected void update(Entity entity, ComponentA1 a1, ComponentA3 a3) {
        System.out.println(this.getClass().getSimpleName() +" update at: " + getWorld().getCurrentTime());
        a1.data += "a3";
    }
}
