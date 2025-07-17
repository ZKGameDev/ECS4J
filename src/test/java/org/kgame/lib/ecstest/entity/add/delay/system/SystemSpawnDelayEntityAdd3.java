package org.kgame.lib.ecstest.entity.add.delay.system;

import org.kgame.lib.ecs.Entity;
import org.kgame.lib.ecs.EcsComponent;
import org.kgame.lib.ecs.annotation.UpdateInGroup;
import org.kgame.lib.ecs.extensions.system.EcsInitializeSystem;
import org.kgame.lib.ecstest.entity.add.delay.component.EntityDelayAdd3;
import org.kgame.lib.ecstest.entity.add.delay.group.SysGroupEntityDelayAddSpawn;

import java.util.Collection;
import java.util.Collections;

@UpdateInGroup(SysGroupEntityDelayAddSpawn.class)
public class SystemSpawnDelayEntityAdd3 extends EcsInitializeSystem<EntityDelayAdd3> {

    @Override
    public boolean onInitialize(Entity entity, EntityDelayAdd3 component) {
        System.out.println(this.getClass().getSimpleName() + " initialize at: " + getWorld().getCurrentTime());
        component.spawnTime = getWorld().getCurrentTime();
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