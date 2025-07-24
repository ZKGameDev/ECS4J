package top.kgame.lib.ecstest.dispose.system;

import top.kgame.lib.ecs.Entity;
import top.kgame.lib.ecs.annotation.UpdateInGroup;
import top.kgame.lib.ecs.annotation.UpdateIntervalTime;
import top.kgame.lib.ecs.extensions.system.EcsUpdateSystemOne;
import top.kgame.lib.ecstest.dispose.component.ComponentDispose3;
import top.kgame.lib.ecstest.dispose.group.SysGroupDisposeTest;

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