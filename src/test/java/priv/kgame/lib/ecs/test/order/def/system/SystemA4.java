package priv.kgame.lib.ecs.test.order.def.system;

import priv.kgame.lib.ecs.EcsWorld;
import priv.kgame.lib.ecs.test.order.def.group.SysGroupA;
import priv.kgame.lib.ecs.entity.Entity;
import priv.kgame.lib.ecs.system.annotation.UpdateInGroup;
import priv.kgame.lib.ecs.system.base.EcsUpdateSystemOne;
import priv.kgame.lib.ecs.test.order.def.component.ComponentA4;

@UpdateInGroup(SysGroupA.class)
public class SystemA4 extends EcsUpdateSystemOne<ComponentA4> {
    public SystemA4(EcsWorld ecsWorld) {
        super(ecsWorld);
    }

    @Override
    protected void update(Entity entity, ComponentA4 component) {
        System.out.println(this.getClass().getSimpleName() +" update at: " + System.currentTimeMillis());
    }
}
