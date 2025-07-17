package org.kgame.lib.ecstest.entity.remove.delay.system;

import org.kgame.lib.ecs.Entity;
import org.kgame.lib.ecs.EcsComponent;
import org.kgame.lib.ecs.annotation.UpdateInGroup;
import org.kgame.lib.ecs.extensions.system.EcsInitializeSystem;
import org.kgame.lib.ecstest.entity.remove.delay.component.EntityDelayRemove2;
import org.kgame.lib.ecstest.entity.remove.delay.group.SysGroupEntityDelayRemoveSpawn;

import java.util.Collection;
import java.util.Collections;

@UpdateInGroup(SysGroupEntityDelayRemoveSpawn.class)
public class SystemSpawnDelayEntityRemove2 extends EcsInitializeSystem<EntityDelayRemove2> {

    @Override
    public boolean onInitialize(Entity entity, EntityDelayRemove2 component) {
        System.out.println(this.getClass().getSimpleName() + " initialize at: " + getWorld().getCurrentTime());
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