package priv.kgame.lib.ecstest.system.order.custom.system;

import priv.kgame.lib.ecs.EcsComponent;
import priv.kgame.lib.ecs.extensions.component.InitializedComponent;
import priv.kgame.lib.ecs.Entity;
import priv.kgame.lib.ecs.annotation.UpdateAfterSystem;
import priv.kgame.lib.ecs.annotation.UpdateInGroup;
import priv.kgame.lib.ecs.extensions.system.EcsInitializeSystem;
import priv.kgame.lib.ecstest.system.order.custom.component.ComponentA1;
import priv.kgame.lib.ecstest.system.order.custom.group.SysGroupCustomOrderSpawn;

import java.util.Collection;
import java.util.List;

@UpdateInGroup(SysGroupCustomOrderSpawn.class)
@UpdateAfterSystem(systemTypes = SystemSpawnCustomOrder3.class)
public class SystemSpawnCustomOrder1 extends EcsInitializeSystem<ComponentA1> {

    @Override
    public boolean onInitialize(Entity entity, ComponentA1 data) {
        System.out.println(this.getClass().getSimpleName() +" update at: " + getWorld().getCurrentTime());
        entity.addComponent(InitializedComponent.generate());
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
        return new SystemInitFinishSingle() {};
    }
}
