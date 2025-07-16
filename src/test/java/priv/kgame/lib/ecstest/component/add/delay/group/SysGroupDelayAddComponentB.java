package priv.kgame.lib.ecstest.component.add.delay.group;

import priv.kgame.lib.ecs.EcsSystemGroup;
import priv.kgame.lib.ecs.annotation.UpdateAfterSystem;

@UpdateAfterSystem(systemTypes = { SysGroupDelayAddComponentA.class })
public class SysGroupDelayAddComponentB extends EcsSystemGroup {

    @Override
    protected void onStart() {

    }

    @Override
    protected void onStop() {

    }
}
