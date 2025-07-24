package top.kgame.lib.ecstest.system.order.custom.system;

import top.kgame.lib.ecs.Entity;
import top.kgame.lib.ecs.annotation.UpdateAfterSystem;
import top.kgame.lib.ecs.annotation.UpdateBeforeSystem;
import top.kgame.lib.ecs.annotation.UpdateInGroup;
import top.kgame.lib.ecs.extensions.system.EcsUpdateSystemOne;
import top.kgame.lib.ecstest.system.order.custom.component.ComponentA1;
import top.kgame.lib.ecstest.system.order.custom.group.SysGroupCustomOrderA;

@UpdateInGroup(SysGroupCustomOrderA.class)
@UpdateAfterSystem(systemTypes = { SystemA2.class })
@UpdateBeforeSystem(systemTypes = { SystemA0.class })
public class SystemA4 extends EcsUpdateSystemOne<ComponentA1> {

    @Override
    protected void update(Entity entity, ComponentA1 component) {
        System.out.println(this.getClass().getSimpleName() +" update at: " + getWorld().getCurrentTime());
        component.data += "a4";
    }
}
