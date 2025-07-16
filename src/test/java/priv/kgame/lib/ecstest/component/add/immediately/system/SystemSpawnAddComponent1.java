package priv.kgame.lib.ecstest.component.add.immediately.system;

import priv.kgame.lib.ecs.EcsComponent;
import priv.kgame.lib.ecs.Entity;
import priv.kgame.lib.ecs.annotation.UpdateAfterSystem;
import priv.kgame.lib.ecs.annotation.UpdateInGroup;
import priv.kgame.lib.ecs.extensions.system.EcsInitializeSystem;
import priv.kgame.lib.ecstest.component.add.immediately.component.ComponentAdd1;
import priv.kgame.lib.ecstest.component.add.immediately.group.SysGroupAddComponentSpawn;

import java.util.Collection;
import java.util.List;

@UpdateInGroup(SysGroupAddComponentSpawn.class)
@UpdateAfterSystem(systemTypes = SystemSpawnAddComponent3.class)
public class SystemSpawnAddComponent1 extends EcsInitializeSystem<ComponentAdd1> {

    @Override
    public boolean onInitialize(Entity entity, ComponentAdd1 data) {
        System.out.println(this.getClass().getSimpleName() + " update at: " + getWorld().getCurrentTime());
        data.data += "o1";
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
        return new SystemInitFinishSingle() {
        };
    }
}
