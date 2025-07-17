package org.kgame.lib.ecstest.entity.remove.immediately.group;

import org.kgame.lib.ecs.EcsSystemGroup;
import org.kgame.lib.ecs.annotation.UpdateAfterSystem;

@UpdateAfterSystem(systemTypes = { SysGroupEntityRemoveSpawn.class })
public class SysGroupEntityRemoveA extends EcsSystemGroup {

    @Override
    protected void onStart() {

    }

    @Override
    protected void onStop() {

    }
} 