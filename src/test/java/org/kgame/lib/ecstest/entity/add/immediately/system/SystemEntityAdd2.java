package org.kgame.lib.ecstest.entity.add.immediately.system;

import org.kgame.lib.ecs.Entity;
import org.kgame.lib.ecs.annotation.UpdateInGroup;
import org.kgame.lib.ecs.extensions.system.EcsUpdateSystemOne;
import org.kgame.lib.ecstest.entity.add.immediately.EcsEntityAddTest;
import org.kgame.lib.ecstest.entity.add.immediately.component.EntityAdd2;
import org.kgame.lib.ecstest.entity.add.immediately.group.SysGroupEntityAddA;

@UpdateInGroup(SysGroupEntityAddA.class)
public class SystemEntityAdd2 extends EcsUpdateSystemOne<EntityAdd2> {

    @Override
    protected void update(Entity entity, EntityAdd2 component) {
        System.out.println(this.getClass().getSimpleName() + " update at: " + getWorld().getCurrentTime());
        component.updateTime = getWorld().getCurrentTime();

        EcsEntityAddTest.LogicContext logicContext = getWorld().getContext();
        if (logicContext.createTime123 == getWorld().getCurrentTime()) {
            logicContext.entity123 = getWorld().createEntity(123);
        }
    }
} 