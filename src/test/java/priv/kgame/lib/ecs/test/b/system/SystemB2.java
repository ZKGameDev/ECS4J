package priv.kgame.lib.ecs.test.b.system;

import priv.kgame.lib.ecs.EcsWorld;
import priv.kgame.lib.ecs.test.b.group.SysGroupB;
import priv.kgame.lib.ecs.entity.Entity;
import priv.kgame.lib.ecs.system.annotation.UpdateInGroup;
import priv.kgame.lib.ecs.system.annotation.UpdateIntervalTime;
import priv.kgame.lib.ecs.system.base.EcsUpdateSystemOne;
import priv.kgame.lib.ecs.test.b.component.ComponentB2;

@UpdateInGroup(SysGroupB.class)
@UpdateIntervalTime(interval=0.2f)
public class SystemB2 extends EcsUpdateSystemOne<ComponentB2> {
    public SystemB2(EcsWorld ecsWorld) {
        super(ecsWorld);
    }

    @Override
    protected void update(Entity entity, ComponentB2 component) {
        System.out.println(this.getClass().getSimpleName() +" update at: " + System.currentTimeMillis());
    }
} 