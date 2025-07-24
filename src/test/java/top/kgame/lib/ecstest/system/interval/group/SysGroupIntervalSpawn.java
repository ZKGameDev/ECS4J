package top.kgame.lib.ecstest.system.interval.group;

import top.kgame.lib.ecs.EcsSystemGroup;
import top.kgame.lib.ecs.annotation.UpdateBeforeSystem;

@UpdateBeforeSystem(systemTypes = { SysGroupInterval.class })
public class SysGroupIntervalSpawn extends EcsSystemGroup {

    @Override
    protected void onStart() {
    }

    @Override
    protected void onStop() {
    }
} 