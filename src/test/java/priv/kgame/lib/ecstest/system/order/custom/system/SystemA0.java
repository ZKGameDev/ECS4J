package priv.kgame.lib.ecstest.system.order.custom.system;

import priv.kgame.lib.ecs.entity.Entity;
import priv.kgame.lib.ecs.system.annotation.UpdateInGroup;
import priv.kgame.lib.ecs.system.base.EcsUpdateSystemOne;
import priv.kgame.lib.ecstest.system.order.custom.component.ComponentA1;
import priv.kgame.lib.ecstest.system.order.custom.component.ComponentA2;
import priv.kgame.lib.ecstest.system.order.custom.group.SysGroupA;

@UpdateInGroup(SysGroupA.class)
public class SystemA0 extends EcsUpdateSystemOne<ComponentA2> {

    @Override
    protected void update(Entity entity, ComponentA2 component) {
        System.out.println(this.getClass().getSimpleName() +" update at: " + getWorld().getCurrentTime());
        ComponentA1 componentA1 = entity.getComponent(ComponentA1.class);
        component.data = componentA1.data;
        componentA1.data = "";
    }
}
