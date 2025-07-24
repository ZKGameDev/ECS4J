package top.kgame.lib.ecstest.dispose.group;

import top.kgame.lib.ecs.EcsSystemGroup;
import top.kgame.lib.ecs.annotation.UpdateAfterSystem;

@UpdateAfterSystem(systemTypes = { SysGroupSpawnDisposeTest.class })
public class SysGroupDisposeTest extends EcsSystemGroup {

    @Override
    protected void onStart() {
    }

    @Override
    protected void onStop() {
    }
} 