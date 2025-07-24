package top.kgame.lib.ecstest.system.mixed.group;

import top.kgame.lib.ecs.EcsSystemGroup;
import top.kgame.lib.ecs.annotation.UpdateBeforeSystem;

@UpdateBeforeSystem(systemTypes = { SysGroupMixSystemA.class })
public class SysGroupMixSystemSpawn extends EcsSystemGroup {

    @Override
    protected void onStart() {

    }

    @Override
    protected void onStop() {

    }
}
