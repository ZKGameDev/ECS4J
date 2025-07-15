package priv.kgame.lib.ecstest.dispose.system;

import priv.kgame.lib.ecs.entity.Entity;
import priv.kgame.lib.ecs.system.annotation.UpdateInGroup;
import priv.kgame.lib.ecs.system.annotation.UpdateIntervalTime;
import priv.kgame.lib.ecs.system.base.EcsUpdateSystemOne;
import priv.kgame.lib.ecstest.dispose.component.ComponentDispose3;
import priv.kgame.lib.ecstest.dispose.group.SysGroupDisposeTest;

@UpdateInGroup(SysGroupDisposeTest.class)
@UpdateIntervalTime(interval=0.033f)
public class SystemDispose3 extends EcsUpdateSystemOne<ComponentDispose3> {

    @Override
    protected void update(Entity entity, ComponentDispose3 component) {
        System.out.println(this.getClass().getSimpleName() +" update at: " + getWorld().getCurrentTime());
        component.updateTime = getWorld().getCurrentTime();
        if (component.disposeTime > 0 && component.disposeTime <= getWorld().getCurrentTime()) {
            getWorld().close();
        }
    }
} 