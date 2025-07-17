package org.kgame.lib.ecstest.entity.add.immediately.system;

import org.kgame.lib.ecs.Entity;
import org.kgame.lib.ecs.EcsComponent;
import org.kgame.lib.ecs.annotation.UpdateInGroup;
import org.kgame.lib.ecs.extensions.system.EcsInitializeSystem;
import org.kgame.lib.ecstest.entity.add.immediately.EcsEntityAddTest;
import org.kgame.lib.ecstest.entity.add.immediately.component.EntityAdd2;
import org.kgame.lib.ecstest.entity.add.immediately.group.SysGroupEntityAddSpawn;

import java.util.Collection;
import java.util.Collections;

@UpdateInGroup(SysGroupEntityAddSpawn.class)
public class SystemSpawnEntityAdd2 extends EcsInitializeSystem<EntityAdd2> {

    @Override
    public boolean onInitialize(Entity entity, EntityAdd2 component) {
        System.out.println(this.getClass().getSimpleName() + " initialize at: " + getWorld().getCurrentTime());
        component.spawnTime = getWorld().getCurrentTime();

        EcsEntityAddTest.LogicContext logicContext = getWorld().getContext();
        if (logicContext.entity12 == null) {
            logicContext.entity12 = getWorld().createEntity(12);
            logicContext.createTime12 = getWorld().getCurrentTime();
        }

        return true;
    }

    @Override
    protected SystemInitFinishSingle getInitFinishSingle() {
        return new SystemInitFinishSingle() {};
    }

    @Override
    public Collection<Class<? extends EcsComponent>> getExtraRequirementComponent() {
        return Collections.emptyList();
    }

    @Override
    public Collection<Class<? extends EcsComponent>> getExtraExcludeComponent() {
        return Collections.emptyList();
    }
} 