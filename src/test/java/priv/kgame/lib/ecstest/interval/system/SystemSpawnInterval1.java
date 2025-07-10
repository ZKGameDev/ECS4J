package priv.kgame.lib.ecstest.interval.system;

import priv.kgame.lib.ecs.component.EcsComponent;
import priv.kgame.lib.ecs.component.base.InitializedComponent;
import priv.kgame.lib.ecs.entity.Entity;
import priv.kgame.lib.ecs.system.annotation.UpdateInGroup;
import priv.kgame.lib.ecs.system.base.EcsInitializeSystem;
import priv.kgame.lib.ecstest.interval.component.ComponentInterval1;
import priv.kgame.lib.ecstest.interval.group.SysGroupSpawnInterval;

import java.util.Collection;
import java.util.List;

@UpdateInGroup(SysGroupSpawnInterval.class)
public class SystemSpawnInterval1 extends EcsInitializeSystem<ComponentInterval1> {

    @Override
    public boolean onInitialize(Entity entity, ComponentInterval1 data) {
        System.out.println(this.getClass().getSimpleName() +" update at: " + getWorld().getCurrentTime());
        getWorld().addComponent(entity, InitializedComponent.generate());
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