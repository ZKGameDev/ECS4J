package priv.kgame.lib.ecstest.dispose.system;

import priv.kgame.lib.ecs.entity.Entity;
import priv.kgame.lib.ecs.system.annotation.UpdateInGroup;
import priv.kgame.lib.ecs.system.base.EcsUpdateSystemOne;
import priv.kgame.lib.ecstest.dispose.component.ComponentDispose1;
import priv.kgame.lib.ecstest.dispose.group.SysGroupDisposeTest;

@UpdateInGroup(SysGroupDisposeTest.class)
public class SystemDispose1 extends EcsUpdateSystemOne<ComponentDispose1> {

    @Override
    protected void update(Entity entity, ComponentDispose1 component) {
        System.out.println(this.getClass().getSimpleName() +" update at: " + getWorld().getCurrentTime());
        component.updateTime = getWorld().getCurrentTime();
        if (component.disposeTime > 0 && component.disposeTime <= getWorld().getCurrentTime()) {
            getWorld().dispose();
        }
    }
}