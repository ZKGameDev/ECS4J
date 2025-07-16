package org.kgame.lib.ecstest.dispose.system;

import org.kgame.lib.ecs.EcsComponent;
import org.kgame.lib.ecs.Entity;
import org.kgame.lib.ecs.annotation.UpdateInGroup;
import org.kgame.lib.ecs.extensions.system.EcsInitializeSystem;
import org.kgame.lib.ecstest.dispose.component.ComponentDispose1;
import org.kgame.lib.ecstest.dispose.group.SysGroupSpawnDisposeTest;

import java.util.Collection;
import java.util.List;

@UpdateInGroup(SysGroupSpawnDisposeTest.class)
public class SystemSpawnDispose1 extends EcsInitializeSystem<ComponentDispose1> {

    @Override
    public boolean onInitialize(Entity entity, ComponentDispose1 data) {
        System.out.println(this.getClass().getSimpleName() +" update at: " + getWorld().getCurrentTime());
        return true;
    }

    @Override
    public Collection<Class<? extends EcsComponent>> getExtraRequirementComponent() {
        return List.of();
    }

    @Override
    public Collection<Class<? extends EcsComponent>> getExtraExcludeComponent() {
        return List.of();
    }

    @Override
    protected SystemInitFinishSingle getInitFinishSingle() {
        return new SystemInitFinishSingle() {};
    }
} 