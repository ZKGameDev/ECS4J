package priv.kgame.lib.ecstest.system.order.custom.group;

import priv.kgame.lib.ecs.EcsSystemGroup;
import priv.kgame.lib.ecs.annotation.UpdateBeforeSystem;

@UpdateBeforeSystem(systemTypes = { SysGroupCustomOrderA.class })
public class SysGroupCustomOrderSpawn extends EcsSystemGroup {

    @Override
    protected void onStart() {

    }

    @Override
    protected void onStop() {

    }
}
