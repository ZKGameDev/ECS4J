package top.kgame.lib.ecstest.entity.add.delay.system;

import top.kgame.lib.ecs.Entity;
import top.kgame.lib.ecs.annotation.UpdateInGroup;
import top.kgame.lib.ecs.command.SystemCommandCreateEntity;
import top.kgame.lib.ecs.extensions.system.EcsUpdateSystemOne;
import top.kgame.lib.ecstest.entity.add.delay.EcsEntityDelayAddTest;
import top.kgame.lib.ecstest.entity.add.delay.component.EntityDelayAdd1;
import top.kgame.lib.ecstest.entity.add.delay.group.SysGroupEntityDelayAddA;

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