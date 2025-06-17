package priv.kgame.lib.ecs.test.order.def.system;

import priv.kgame.lib.ecs.EcsWorld;
import priv.kgame.lib.ecs.test.order.def.group.SysGroupA;
import priv.kgame.lib.ecs.entity.Entity;
import priv.kgame.lib.ecs.system.annotation.UpdateInGroup;
import priv.kgame.lib.ecs.system.base.EcsUpdateSystemOne;
import priv.kgame.lib.ecs.test.order.def.component.ComponentA5;

@UpdateInGroup(SysGroupA.class)
public class SystemA5 extends EcsUpdateSystemOne<ComponentA5> {
    public SystemA5(EcsWorld ecsWorld) {
        super(ecsWorld);
    }

    @Override
    protected void update(Entity entity, ComponentA5 component) {
        System.out.println(this.getClass().getSimpleName() +" update at: " + System.currentTimeMillis());
    }
}
