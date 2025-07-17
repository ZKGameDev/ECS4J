package org.kgame.lib.ecstest.entity.remove.immediately.system;

import org.kgame.lib.ecs.Entity;
import org.kgame.lib.ecs.annotation.UpdateInGroup;
import org.kgame.lib.ecs.extensions.system.EcsUpdateSystemOne;
import org.kgame.lib.ecstest.entity.remove.immediately.component.EntityRemove3;
import org.kgame.lib.ecstest.entity.remove.immediately.group.SysGroupEntityRemoveA;

@UpdateInGroup(SysGroupEntityRemoveA.class)
public class SystemEntityRemove3 extends EcsUpdateSystemOne<EntityRemove3> {

    @Override
    protected void update(Entity entity, EntityRemove3 component) {
        System.out.println(this.getClass().getSimpleName() + " update at: " + getWorld().getCurrentTime());

        if (component.destroyTime > 0 && component.destroyTime == getWorld().getCurrentTime()) {
            getWorld().requestDestroyEntity(entity);
        }

        component.updateTime = getWorld().getCurrentTime();
    }
} 