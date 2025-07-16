package org.kgame.lib.ecstest.system.interval.group;

import org.kgame.lib.ecs.EcsSystemGroup;
import org.kgame.lib.ecs.annotation.UpdateBeforeSystem;

@UpdateBeforeSystem(systemTypes = { SysGroupInterval.class })
public class SysGroupIntervalSpawn extends EcsSystemGroup {

    @Override
    protected void onStart() {
    }

    @Override
    protected void onStop() {
    }
} 