package priv.kgame.lib.ecstest.system.order.def.group;

import priv.kgame.lib.ecs.EcsSystemGroup;
import priv.kgame.lib.ecs.annotation.UpdateBeforeSystem;

@UpdateBeforeSystem(systemTypes = { SysGroupDefOrderA.class })
public class SysGroupDefOrderSpawn extends EcsSystemGroup {

    @Override
    protected void onStart() {

    }

    @Override
    protected void onStop() {

    }
}
