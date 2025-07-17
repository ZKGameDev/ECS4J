package org.kgame.lib.ecstest.entity.add.delay.group;

import org.kgame.lib.ecs.EcsSystemGroup;
import org.kgame.lib.ecs.annotation.UpdateAfterSystem;
import org.kgame.lib.ecs.annotation.UpdateBeforeSystem;

@UpdateBeforeSystem(systemTypes = { SysGroupEntityDelayAddSpawn.class })
public class SysGroupEntityDelayAddA extends EcsSystemGroup {

    @Override
    protected void onStart() {

    }

    @Override
    protected void onStop() {

    }
} 