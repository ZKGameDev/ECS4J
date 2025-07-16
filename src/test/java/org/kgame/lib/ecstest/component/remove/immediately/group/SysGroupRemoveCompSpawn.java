package org.kgame.lib.ecstest.component.remove.immediately.group;

import org.kgame.lib.ecs.EcsSystemGroup;
import org.kgame.lib.ecs.annotation.UpdateBeforeSystem;

@UpdateBeforeSystem(systemTypes = { SysGroupRemoveCompA.class })
public class SysGroupRemoveCompSpawn extends EcsSystemGroup {

    @Override
    protected void onStart() {

    }

    @Override
    protected void onStop() {

    }
}
