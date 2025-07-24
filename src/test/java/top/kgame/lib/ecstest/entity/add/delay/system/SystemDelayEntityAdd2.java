package top.kgame.lib.ecstest.entity.add.delay.system;

import top.kgame.lib.ecs.Entity;
import top.kgame.lib.ecs.annotation.UpdateInGroup;
import top.kgame.lib.ecs.extensions.system.EcsUpdateSystemOne;
import top.kgame.lib.ecstest.entity.add.delay.component.EntityDelayAdd2;
import top.kgame.lib.ecstest.entity.add.delay.group.SysGroupEntityDelayAddA;

@UpdateInGroup(SysGroupEntityDelayAddA.class)
public class SystemDelayEntityAdd2 extends EcsUpdateSystemOne<EntityDelayAdd2> {

    @Override
    protected void update(Entity entity, EntityDelayAdd2 component) {
        System.out.println(this.getClass().getSimpleName() + " update at: " + getWorld().getCurrentTime());
        component.updateTime = getWorld().getCurrentTime();
    }
} 