package priv.kgame.lib.ecstest.component.remove.system;

import priv.kgame.lib.ecs.component.EcsComponent;
import priv.kgame.lib.ecs.component.base.InitializedComponent;
import priv.kgame.lib.ecs.entity.Entity;
import priv.kgame.lib.ecs.system.annotation.UpdateInGroup;
import priv.kgame.lib.ecs.system.base.EcsInitializeSystem;
import priv.kgame.lib.ecstest.component.remove.component.ComponentA1;
import priv.kgame.lib.ecstest.component.remove.component.ComponentA3;
import priv.kgame.lib.ecstest.component.remove.group.SysGroupSpawn;

import java.util.Collection;
import java.util.List;

@UpdateInGroup(SysGroupSpawn.class)
public class SystemSpawnCustomOrder3 extends EcsInitializeSystem<ComponentA3> {

    @Override
    public boolean onInitialize(Entity entity, ComponentA3 data) {
        System.out.println(this.getClass().getSimpleName() +" update at: " + getWorld().getCurrentTime());
        getWorld().addComponent(entity, InitializedComponent.generate());
        ComponentA1 componentA1 = entity.getComponent(ComponentA1.class);
        componentA1.data += "o3";
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
