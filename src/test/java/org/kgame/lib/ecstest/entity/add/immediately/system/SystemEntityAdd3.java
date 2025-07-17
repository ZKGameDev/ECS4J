package org.kgame.lib.ecstest.entity.add.immediately.system;

import org.kgame.lib.ecs.Entity;
import org.kgame.lib.ecs.annotation.UpdateInGroup;
import org.kgame.lib.ecs.extensions.system.EcsUpdateSystemOne;
import org.kgame.lib.ecstest.entity.add.immediately.component.EntityAdd3;
import org.kgame.lib.ecstest.entity.add.immediately.group.SysGroupEntityAddA;

@UpdateInGroup(SysGroupEntityAddA.class)
public class SystemEntityAdd3 extends EcsUpdateSystemOne<EntityAdd3> {

    @Override
    protected void update(Entity entity, EntityAdd3 component) {
        System.out.println(this.getClass().getSimpleName() + " update at: " + getWorld().getCurrentTime());
        component.updateTime = getWorld().getCurrentTime();
    }
} 