package top.kgame.lib.ecstest.entity.remove.delay.system;

import top.kgame.lib.ecs.Entity;
import top.kgame.lib.ecs.annotation.UpdateInGroup;
import top.kgame.lib.ecs.command.EcsCommandScope;
import top.kgame.lib.ecs.command.SystemCommandDestroyEntity;
import top.kgame.lib.ecs.extensions.system.EcsUpdateSystemOne;
import top.kgame.lib.ecstest.entity.remove.delay.component.EntityDelayRemove3;
import top.kgame.lib.ecstest.entity.remove.delay.group.SysGroupEntityDelayRemoveA;

@UpdateInGroup(SysGroupEntityDelayRemoveA.class)
public class SystemDelayEntityRemove3 extends EcsUpdateSystemOne<EntityDelayRemove3> {

    @Override
    protected void update(Entity entity, EntityDelayRemove3 component) {
        System.out.println(this.getClass().getSimpleName() + " update at: " + getWorld().getCurrentTime());
        if (component.destroyTime > 0 && component.destroyTime == getWorld().getCurrentTime()) {
            EcsCommandScope scope = getWorld().getContext();
            addDelayCommand(new SystemCommandDestroyEntity(getWorld(), entity), scope);
        }

        component.updateTime = getWorld().getCurrentTime();
    }
} 