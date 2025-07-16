package org.kgame.lib.ecstest.component.remove.delay.group;

import org.kgame.lib.ecs.EcsSystemGroup;
import org.kgame.lib.ecs.annotation.UpdateAfterSystem;

@UpdateAfterSystem(systemTypes = { SysGroupDelayRemoveComponentSpawn.class })
public class SysGroupDelayRemoveComponentA extends EcsSystemGroup {

    @Override
    protected void onStart() {

    }

    @Override
    protected void onStop() {

    }
} 