package org.kgame.lib.ecstest.dispose.system;

import org.kgame.lib.ecs.Entity;
import org.kgame.lib.ecs.annotation.UpdateInGroup;
import org.kgame.lib.ecs.extensions.system.EcsUpdateSystemOne;
import org.kgame.lib.ecstest.dispose.component.ComponentDispose1;
import org.kgame.lib.ecstest.dispose.group.SysGroupDisposeTest;

@UpdateInGroup(SysGroupDisposeTest.class)
public class SystemDispose1 extends EcsUpdateSystemOne<ComponentDispose1> {

    @Override
    protected void update(Entity entity, ComponentDispose1 component) {
        System.out.println(this.getClass().getSimpleName() +" update at: " + getWorld().getCurrentTime());
        component.updateTime = getWorld().getCurrentTime();
        if (component.disposeTime > 0 && component.disposeTime <= getWorld().getCurrentTime()) {
            getWorld().close();
        }
    }
}