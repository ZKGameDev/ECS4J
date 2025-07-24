package top.kgame.lib.ecstest.component.add.delay.system;

import top.kgame.lib.ecs.EcsComponent;
import top.kgame.lib.ecs.Entity;
import top.kgame.lib.ecs.annotation.UpdateInGroup;
import top.kgame.lib.ecs.extensions.system.EcsInitializeSystem;
import top.kgame.lib.ecstest.component.add.delay.component.ComponentDelayAdd1;
import top.kgame.lib.ecstest.component.add.delay.component.ComponentDelayAdd3;
import top.kgame.lib.ecstest.component.add.delay.group.SysGroupDelayAddComponentSpawn;

import java.util.Collection;
import java.util.List;

@UpdateInGroup(SysGroupDelayAddComponentSpawn.class)
public class SystemSpawnDelayAddComponent3 extends EcsInitializeSystem<ComponentDelayAdd3> {

    @Override
    public boolean onInitialize(Entity entity, ComponentDelayAdd3 data) {
        System.out.println(this.getClass().getSimpleName() +" update at: " + getWorld().getCurrentTime());
        ComponentDelayAdd1 a1 = entity.getComponent(ComponentDelayAdd1.class);
        a1.data += "o3";
        return true;
    }

    @Override
    public Collection<Class<? extends EcsComponent>> getExtraRequirementComponent() {
        return List.of();
    }

    @Override
    public Collection<Class<? extends EcsComponent>> getExtraExcludeComponent() {
        return List.of();
    }

    @Override
    protected SystemInitFinishSingle getInitFinishSingle() {
        return new SystemInitFinishSingle() {};
    }
}
