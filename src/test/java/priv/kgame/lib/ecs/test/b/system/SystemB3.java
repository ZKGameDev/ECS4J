package priv.kgame.lib.ecs.test.b.system;

import priv.kgame.lib.ecs.EcsWorld;
import priv.kgame.lib.ecs.test.b.group.SysGroupB;
import priv.kgame.lib.ecs.entity.Entity;
import priv.kgame.lib.ecs.system.annotation.UpdateInGroup;
import priv.kgame.lib.ecs.system.annotation.UpdateIntervalTime;
import priv.kgame.lib.ecs.system.base.EcsUpdateSystemOne;
import priv.kgame.lib.ecs.test.b.component.ComponentB3;

@UpdateInGroup(SysGroupB.class)
@UpdateIntervalTime(interval=0.66f)
public class SystemB3 extends EcsUpdateSystemOne<ComponentB3> {
    public SystemB3(EcsWorld ecsWorld) {
        super(ecsWorld);
    }

    @Override
    protected void update(Entity entity, ComponentB3 component) {
        System.out.println(this.getClass().getSimpleName() +" update at: " + System.currentTimeMillis());
    }
} 