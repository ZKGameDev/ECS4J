package priv.kgame.lib.ecstest.component.add.immediately.group;

import priv.kgame.lib.ecs.EcsSystemGroup;
import priv.kgame.lib.ecs.annotation.UpdateAfterSystem;

@UpdateAfterSystem(systemTypes = { SysGroupAddComponentSpawn.class })
public class SysGroupAddComponentA extends EcsSystemGroup {

    @Override
    protected void onStart() {

    }

    @Override
    protected void onStop() {

    }
}
