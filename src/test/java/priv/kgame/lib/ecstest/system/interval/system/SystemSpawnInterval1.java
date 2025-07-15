package priv.kgame.lib.ecstest.system.interval.system;

import priv.kgame.lib.ecs.EcsComponent;
import priv.kgame.lib.ecs.extensions.component.InitializedComponent;
import priv.kgame.lib.ecs.Entity;
import priv.kgame.lib.ecs.annotation.UpdateInGroup;
import priv.kgame.lib.ecs.extensions.system.EcsInitializeSystem;
import priv.kgame.lib.ecstest.system.interval.component.ComponentInterval1;
import priv.kgame.lib.ecstest.system.interval.group.SysGroupIntervalSpawn;

import java.util.Collection;
import java.util.List;

@UpdateInGroup(SysGroupIntervalSpawn.class)
public class SystemSpawnInterval1 extends EcsInitializeSystem<ComponentInterval1> {

    @Override
    public boolean onInitialize(Entity entity, ComponentInterval1 data) {
        System.out.println(this.getClass().getSimpleName() +" update at: " + getWorld().getCurrentTime());
        entity.addComponent(InitializedComponent.generate());
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