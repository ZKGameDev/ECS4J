package priv.kgame.lib.ecstest.system.mixed.group;

import priv.kgame.lib.ecs.EcsSystemGroup;
import priv.kgame.lib.ecs.annotation.UpdateAfterSystem;

@UpdateAfterSystem(systemTypes = { EcsSystemGroup.class })
public class SysGroupMixSystemA extends EcsSystemGroup {

    @Override
    protected void onStart() {

    }

    @Override
    protected void onStop() {

    }
}
