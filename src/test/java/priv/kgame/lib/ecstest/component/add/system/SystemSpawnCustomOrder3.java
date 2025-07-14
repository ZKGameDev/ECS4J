package priv.kgame.lib.ecstest.component.add.system;

import priv.kgame.lib.ecs.component.EcsComponent;
import priv.kgame.lib.ecs.component.base.InitializedComponent;
import priv.kgame.lib.ecs.entity.Entity;
import priv.kgame.lib.ecs.system.annotation.UpdateInGroup;
import priv.kgame.lib.ecs.system.base.EcsInitializeSystem;
import priv.kgame.lib.ecstest.component.add.component.ComponentA1;
import priv.kgame.lib.ecstest.component.add.component.ComponentA3;
import priv.kgame.lib.ecstest.component.add.group.SysGroupSpawn;

import java.util.Collection;
import java.util.List;

@UpdateInGroup(SysGroupSpawn.class)
public class SystemSpawnCustomOrder3 extends EcsInitializeSystem<ComponentA3> {

    @Override
    public boolean onInitialize(Entity entity, ComponentA3 data) {
        System.out.println(this.getClass().getSimpleName() +" update at: " + getWorld().getCurrentTime());
        getWorld().addComponent(entity, InitializedComponent.generate());
        data.data += "o3";
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
