package top.kgame.lib.ecstest.entity.remove.delay.group;

import top.kgame.lib.ecs.EcsSystemGroup;
import top.kgame.lib.ecs.annotation.UpdateAfterSystem;

@UpdateAfterSystem(systemTypes = { SysGroupEntityDelayRemoveSpawn.class })
public class SysGroupEntityDelayRemoveA extends EcsSystemGroup {

    @Override
    protected void onStart() {

    }

    @Override
    protected void onStop() {

    }
} 