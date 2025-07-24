package top.kgame.lib.ecstest.entity.add.delay.system;

import top.kgame.lib.ecs.Entity;
import top.kgame.lib.ecs.annotation.UpdateInGroup;
import top.kgame.lib.ecs.command.SystemCommandCreateEntity;
import top.kgame.lib.ecs.extensions.system.EcsUpdateSystemOne;
import top.kgame.lib.ecstest.entity.add.delay.EcsEntityDelayAddTest;
import top.kgame.lib.ecstest.entity.add.delay.component.EntityDelayAdd3;
import top.kgame.lib.ecstest.entity.add.delay.group.SysGroupEntityDelayAddA;

@UpdateInGroup(SysGroupEntityDelayAddA.class)
public class SystemDelayEntityAdd3 extends EcsUpdateSystemOne<EntityDelayAdd3> {

    @Override
    protected void update(Entity entity, EntityDelayAdd3 component) {
        System.out.println(this.getClass().getSimpleName() + " update at: " + getWorld().getCurrentTime());
        component.updateTime = getWorld().getCurrentTime();

        EcsEntityDelayAddTest.LogicContext context = getWorld().getContext();
        if (context.entity1CreateTime == getWorld().getCurrentTime()) {
            addDelayCommand(new SystemCommandCreateEntity(getWorld(), 1, it -> {context.entity1 = it;}), context.level);
        }
    }
} 