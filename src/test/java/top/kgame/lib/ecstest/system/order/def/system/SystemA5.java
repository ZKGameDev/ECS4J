package top.kgame.lib.ecstest.system.order.def.system;

import top.kgame.lib.ecs.Entity;
import top.kgame.lib.ecs.annotation.UpdateInGroup;
import top.kgame.lib.ecs.extensions.system.EcsUpdateSystemOne;
import top.kgame.lib.ecstest.system.order.def.component.ComponentA1;
import top.kgame.lib.ecstest.system.order.def.component.ComponentA2;
import top.kgame.lib.ecstest.system.order.def.group.SysGroupDefOrderA;

@UpdateInGroup(SysGroupDefOrderA.class)
public class SystemA5 extends EcsUpdateSystemOne<ComponentA2> {

    @Override
    protected void update(Entity entity, ComponentA2 component) {
        System.out.println(this.getClass().getSimpleName() +" update at: " + getWorld().getCurrentTime());
        ComponentA1 componentA1 = entity.getComponent(ComponentA1.class);
        component.data = componentA1.data;
        componentA1.data = "";
    }
}
