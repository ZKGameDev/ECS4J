package org.kgame.lib.ecstest.dispose.group;

import org.kgame.lib.ecs.EcsSystemGroup;
import org.kgame.lib.ecs.annotation.UpdateAfterSystem;

@UpdateAfterSystem(systemTypes = { SysGroupSpawnDisposeTest.class })
public class SysGroupDisposeTest extends EcsSystemGroup {

    @Override
    protected void onStart() {
    }

    @Override
    protected void onStop() {
    }
} 