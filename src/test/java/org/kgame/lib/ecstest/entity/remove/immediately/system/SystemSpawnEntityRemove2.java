package org.kgame.lib.ecstest.entity.remove.immediately.system;

import org.kgame.lib.ecs.Entity;
import org.kgame.lib.ecs.EcsComponent;
import org.kgame.lib.ecs.annotation.UpdateInGroup;
import org.kgame.lib.ecs.extensions.system.EcsInitializeSystem;
import org.kgame.lib.ecstest.entity.remove.immediately.component.EntityRemove2;
import org.kgame.lib.ecstest.entity.remove.immediately.group.SysGroupEntityRemoveSpawn;

import java.util.Collection;
import java.util.Collections;

@UpdateInGroup(SysGroupEntityRemoveSpawn.class)
public class SystemSpawnEntityRemove2 extends EcsInitializeSystem<EntityRemove2> {

    @Override
    public boolean onInitialize(Entity entity, EntityRemove2 component) {
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