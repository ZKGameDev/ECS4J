package priv.kgame.lib.ecstest.component.add.delay.group;

import priv.kgame.lib.ecs.EcsSystemGroup;
import priv.kgame.lib.ecs.annotation.UpdateAfterSystem;

@UpdateAfterSystem(systemTypes = { SysGroupDelayAddComponentSpawn.class })
public class SysGroupDelayAddComponentA extends EcsSystemGroup {

    @Override
    protected void onStart() {

    }

    @Override
    protected void onStop() {

    }
}
