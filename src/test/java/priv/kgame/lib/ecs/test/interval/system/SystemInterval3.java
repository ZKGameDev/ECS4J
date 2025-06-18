package priv.kgame.lib.ecs.test.interval.system;

import priv.kgame.lib.ecs.EcsWorld;
import priv.kgame.lib.ecs.test.interval.group.SysGroupInterval;
import priv.kgame.lib.ecs.entity.Entity;
import priv.kgame.lib.ecs.system.annotation.UpdateInGroup;
import priv.kgame.lib.ecs.system.annotation.UpdateIntervalTime;
import priv.kgame.lib.ecs.system.base.EcsUpdateSystemOne;
import priv.kgame.lib.ecs.test.interval.component.ComponentInterval3;

@UpdateInGroup(SysGroupInterval.class)
@UpdateIntervalTime(interval=0.66f)
public class SystemInterval3 extends EcsUpdateSystemOne<ComponentInterval3> {
    public SystemInterval3(EcsWorld ecsWorld) {
        super(ecsWorld);
    }

    @Override
    protected void update(Entity entity, ComponentInterval3 component) {
        System.out.println(this.getClass().getSimpleName() +" update at: " + System.currentTimeMillis());
    }
} 