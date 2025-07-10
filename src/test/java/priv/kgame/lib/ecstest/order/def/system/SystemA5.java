package priv.kgame.lib.ecstest.order.def.system;

import priv.kgame.lib.ecs.entity.Entity;
import priv.kgame.lib.ecs.system.annotation.UpdateInGroup;
import priv.kgame.lib.ecs.system.base.EcsUpdateSystemOne;
import priv.kgame.lib.ecstest.order.def.component.ComponentA1;
import priv.kgame.lib.ecstest.order.def.component.ComponentA2;
import priv.kgame.lib.ecstest.order.def.group.SysGroupA;

@UpdateInGroup(SysGroupA.class)
public class SystemA5 extends EcsUpdateSystemOne<ComponentA2> {

    @Override
    protected void update(Entity entity, ComponentA2 component) {
        System.out.println(this.getClass().getSimpleName() +" update at: " + System.currentTimeMillis());
        ComponentA1 componentA1 = entity.getComponent(ComponentA1.class);
        component.data = componentA1.data;
    }
}
