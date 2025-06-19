package priv.kgame.lib.ecs.test.order.def.system;

import priv.kgame.lib.ecs.EcsWorld;
import priv.kgame.lib.ecs.test.order.def.component.ComponentA1;
import priv.kgame.lib.ecs.test.order.def.group.SysGroupA;
import priv.kgame.lib.ecs.entity.Entity;
import priv.kgame.lib.ecs.system.annotation.UpdateInGroup;
import priv.kgame.lib.ecs.system.base.EcsUpdateSystemOne;
import priv.kgame.lib.ecs.test.order.def.component.ComponentA2;

@UpdateInGroup(SysGroupA.class)
public class SystemA2 extends EcsUpdateSystemOne<ComponentA1> {
    public SystemA2(EcsWorld ecsWorld) {
        super(ecsWorld);
    }

    @Override
    protected void update(Entity entity, ComponentA1 component) {
        System.out.println(this.getClass().getSimpleName() +" update at: " + System.currentTimeMillis());
        component.data += "a2";
    }
}
