package priv.kgame.lib.ecs.test.a.system;

import priv.kgame.lib.ecs.EcsWorld;
import priv.kgame.lib.ecs.test.a.group.SysGroupA;
import priv.kgame.lib.ecs.entity.Entity;
import priv.kgame.lib.ecs.system.annotation.UpdateInGroup;
import priv.kgame.lib.ecs.system.annotation.UpdateIntervalTime;
import priv.kgame.lib.ecs.system.base.EcsUpdateSystemOne;
import priv.kgame.lib.ecs.test.a.component.ComponentA3;

@UpdateInGroup(SysGroupA.class)
@UpdateIntervalTime(interval=0.1f)
public class SystemA3 extends EcsUpdateSystemOne<ComponentA3> {
    public SystemA3(EcsWorld ecsWorld) {
        super(ecsWorld);
    }

    @Override
    protected void update(Entity entity, ComponentA3 component) {
        System.out.println(this.getClass().getSimpleName() +" update at: " + System.currentTimeMillis());
    }
}
