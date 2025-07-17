package org.kgame.lib.ecstest.entity.add.immediately.group;

import org.kgame.lib.ecs.EcsSystemGroup;
import org.kgame.lib.ecs.annotation.UpdateAfterSystem;
import org.kgame.lib.ecs.annotation.UpdateBeforeSystem;

@UpdateBeforeSystem(systemTypes = { SysGroupEntityAddSpawn.class })
public class SysGroupEntityAddA extends EcsSystemGroup {

    @Override
    protected void onStart() {

    }


    @Override
    protected void onStop() {

    }
} 