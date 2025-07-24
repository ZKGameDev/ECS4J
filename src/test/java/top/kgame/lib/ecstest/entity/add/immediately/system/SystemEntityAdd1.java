package top.kgame.lib.ecstest.entity.add.immediately.system;

import top.kgame.lib.ecs.Entity;
import top.kgame.lib.ecs.annotation.UpdateInGroup;
import top.kgame.lib.ecs.extensions.system.EcsUpdateSystemOne;
import top.kgame.lib.ecstest.entity.add.immediately.EcsEntityAddTest;
import top.kgame.lib.ecstest.entity.add.immediately.component.EntityAdd1;
import top.kgame.lib.ecstest.entity.add.immediately.group.SysGroupEntityAddA;

@UpdateInGroup(SysGroupEntityAddA.class)
public class SystemEntityAdd1 extends EcsUpdateSystemOne<EntityAdd1> {

    @Override
    protected void update(Entity entity, EntityAdd1 component) {
        System.out.println(this.getClass().getSimpleName() + " update at: " + getWorld().getCurrentTime());
        component.updateTime = getWorld().getCurrentTime();
        EcsEntityAddTest.LogicContext logicContext = getWorld().getContext();
        if (logicContext.createTime1 == getWorld().getCurrentTime()) {
            logicContext.entity1 = getWorld().createEntity(1);
        }
    }
} 