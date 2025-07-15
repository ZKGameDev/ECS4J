package priv.kgame.lib.ecstest.system.interval.group;

import priv.kgame.lib.ecs.EcsSystemGroup;
import priv.kgame.lib.ecs.annotation.UpdateBeforeSystem;

@UpdateBeforeSystem(systemTypes = { SysGroupInterval.class })
public class SysGroupIntervalSpawn extends EcsSystemGroup {

    @Override
    protected void onStart() {
    }

    @Override
    protected void onStop() {
    }
} 