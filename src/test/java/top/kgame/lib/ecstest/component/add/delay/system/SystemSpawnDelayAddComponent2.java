package top.kgame.lib.ecstest.component.add.delay.system;

import top.kgame.lib.ecs.EcsComponent;
import top.kgame.lib.ecs.Entity;
import top.kgame.lib.ecs.annotation.UpdateAfterSystem;
import top.kgame.lib.ecs.annotation.UpdateInGroup;
import top.kgame.lib.ecs.extensions.system.EcsInitializeSystem;
import top.kgame.lib.ecstest.component.add.delay.component.ComponentDelayAdd1;
import top.kgame.lib.ecstest.component.add.delay.component.ComponentDelayAdd2;
import top.kgame.lib.ecstest.component.add.delay.group.SysGroupDelayAddComponentSpawn;

import java.util.Collection;
import java.util.List;

@UpdateInGroup(SysGroupDelayAddComponentSpawn.class)
@UpdateAfterSystem(systemTypes = SystemSpawnDelayAddComponent3.class)
public class SystemSpawnDelayAddComponent2 extends EcsInitializeSystem<ComponentDelayAdd2> {

    @Override
    public boolean onInitialize(Entity entity, ComponentDelayAdd2 data) {
        System.out.println(this.getClass().getSimpleName() +" update at: " + getWorld().getCurrentTime());
        ComponentDelayAdd1 componentDelayAdd1 = entity.getComponent(ComponentDelayAdd1.class);
        componentDelayAdd1.data += "o2";
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
