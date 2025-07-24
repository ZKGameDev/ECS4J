package top.kgame.lib.ecstest.entity.add.immediately.group;

import top.kgame.lib.ecs.EcsSystemGroup;
import top.kgame.lib.ecs.annotation.UpdateBeforeSystem;

@UpdateBeforeSystem(systemTypes = { SysGroupEntityAddSpawn.class })
public class SysGroupEntityAddA extends EcsSystemGroup {

    @Override
    protected void onStart() {

    }


    @Override
    protected void onStop() {

    }
} 