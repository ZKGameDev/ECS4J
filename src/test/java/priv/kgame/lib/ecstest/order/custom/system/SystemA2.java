package priv.kgame.lib.ecstest.order.custom.system;

import priv.kgame.lib.ecs.entity.Entity;
import priv.kgame.lib.ecs.system.annotation.UpdateAfterSystem;
import priv.kgame.lib.ecs.system.annotation.UpdateInGroup;
import priv.kgame.lib.ecs.system.base.EcsUpdateSystemOne;
import priv.kgame.lib.ecstest.order.custom.component.ComponentA1;
import priv.kgame.lib.ecstest.order.custom.group.SysGroupA;

@UpdateInGroup(SysGroupA.class)
@UpdateAfterSystem(systemTypes = { SystemA5.class })
public class SystemA2 extends EcsUpdateSystemOne<ComponentA1> {

    @Override
    protected void update(Entity entity, ComponentA1 component) {
        System.out.println(this.getClass().getSimpleName() +" update at: " + getWorld().getCurrentTime());
        component.data += "a2";
    }
}
