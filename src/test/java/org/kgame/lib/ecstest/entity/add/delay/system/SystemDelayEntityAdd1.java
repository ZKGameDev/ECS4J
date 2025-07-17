package org.kgame.lib.ecstest.entity.add.delay.system;

import org.kgame.lib.ecs.Entity;
import org.kgame.lib.ecs.annotation.UpdateInGroup;
import org.kgame.lib.ecs.command.SystemCommandCreateEntity;
import org.kgame.lib.ecs.extensions.system.EcsUpdateSystemOne;
import org.kgame.lib.ecstest.entity.add.delay.EcsEntityDelayAddTest;
import org.kgame.lib.ecstest.entity.add.delay.component.EntityDelayAdd1;
import org.kgame.lib.ecstest.entity.add.delay.group.SysGroupEntityDelayAddA;

@UpdateInGroup(SysGroupEntityDelayAddA.class)
public class SystemDelayEntityAdd1 extends EcsUpdateSystemOne<EntityDelayAdd1> {

    @Override
    protected void update(Entity entity, EntityDelayAdd1 component) {
        System.out.println(this.getClass().getSimpleName() + " update at: " + getWorld().getCurrentTime());
        component.updateTime = getWorld().getCurrentTime();

        EcsEntityDelayAddTest.LogicContext context = getWorld().getContext();
        if (context.entity123CreateTime == getWorld().getCurrentTime()) {
            addDelayCommand(new SystemCommandCreateEntity(getWorld(), 123, it -> {context.entity123 = it;}), context.level);
        }
    }
} 