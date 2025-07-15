package priv.kgame.lib.ecstest.system.order.custom.system;

import priv.kgame.lib.ecs.Entity;
import priv.kgame.lib.ecs.annotation.UpdateAfterSystem;
import priv.kgame.lib.ecs.annotation.UpdateInGroup;
import priv.kgame.lib.ecs.extensions.system.EcsUpdateSystemOne;
import priv.kgame.lib.ecstest.system.order.custom.component.ComponentA1;
import priv.kgame.lib.ecstest.system.order.custom.group.SysGroupCustomOrderA;

@UpdateInGroup(SysGroupCustomOrderA.class)
@UpdateAfterSystem(systemTypes = { SystemA5.class })
public class SystemA2 extends EcsUpdateSystemOne<ComponentA1> {

    @Override
    protected void update(Entity entity, ComponentA1 component) {
        System.out.println(this.getClass().getSimpleName() +" update at: " + getWorld().getCurrentTime());
        component.data += "a2";
    }
}
