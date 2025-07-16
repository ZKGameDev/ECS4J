package priv.kgame.lib.ecstest.system.mixed.group;

import priv.kgame.lib.ecs.EcsSystemGroup;
import priv.kgame.lib.ecs.annotation.UpdateBeforeSystem;

@UpdateBeforeSystem(systemTypes = { SysGroupMixSystemA.class })
public class SysGroupMixSystemSpawn extends EcsSystemGroup {

    @Override
    protected void onStart() {

    }

    @Override
    protected void onStop() {

    }
}
