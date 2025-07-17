package org.kgame.lib.ecstest.entity.remove.immediately.system;

import org.kgame.lib.ecs.Entity;
import org.kgame.lib.ecs.annotation.UpdateInGroup;
import org.kgame.lib.ecs.extensions.system.EcsUpdateSystemOne;
import org.kgame.lib.ecstest.entity.remove.immediately.component.EntityRemove2;
import org.kgame.lib.ecstest.entity.remove.immediately.group.SysGroupEntityRemoveA;

@UpdateInGroup(SysGroupEntityRemoveA.class)
public class SystemEntityRemove2 extends EcsUpdateSystemOne<EntityRemove2> {

    @Override
    protected void update(Entity entity, EntityRemove2 component) {
        System.out.println(this.getClass().getSimpleName() + " update at: " + getWorld().getCurrentTime());

        if (component.destroyTime > 0 && component.destroyTime == getWorld().getCurrentTime()) {
            getWorld().requestDestroyEntity(entity.getIndex());
        }

        component.updateTime = getWorld().getCurrentTime();
    }
} 