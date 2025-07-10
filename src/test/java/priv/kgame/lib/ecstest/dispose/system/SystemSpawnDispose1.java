package priv.kgame.lib.ecstest.dispose.system;

import priv.kgame.lib.ecs.component.EcsComponent;
import priv.kgame.lib.ecs.component.base.InitializedComponent;
import priv.kgame.lib.ecs.entity.Entity;
import priv.kgame.lib.ecs.system.annotation.UpdateInGroup;
import priv.kgame.lib.ecs.system.base.EcsInitializeSystem;
import priv.kgame.lib.ecstest.dispose.component.ComponentDispose1;
import priv.kgame.lib.ecstest.dispose.group.SysGroupSpawnDisposeTest;

import java.util.Collection;
import java.util.List;

@UpdateInGroup(SysGroupSpawnDisposeTest.class)
public class SystemSpawnDispose1 extends EcsInitializeSystem<ComponentDispose1> {

    @Override
    public boolean onInitialize(Entity entity, ComponentDispose1 data) {
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