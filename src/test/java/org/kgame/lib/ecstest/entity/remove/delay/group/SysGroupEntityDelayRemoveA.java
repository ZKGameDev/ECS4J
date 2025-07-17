package org.kgame.lib.ecstest.entity.remove.delay.group;

import org.kgame.lib.ecs.EcsSystemGroup;
import org.kgame.lib.ecs.annotation.UpdateAfterSystem;

@UpdateAfterSystem(systemTypes = { SysGroupEntityDelayRemoveSpawn.class })
public class SysGroupEntityDelayRemoveA extends EcsSystemGroup {

    @Override
    protected void onStart() {

    }

    @Override
    protected void onStop() {

    }
} 