package priv.kgame.lib.ecstest.component.add.system;

import priv.kgame.lib.ecs.EcsComponent;
import priv.kgame.lib.ecs.Entity;
import priv.kgame.lib.ecs.annotation.UpdateAfterSystem;
import priv.kgame.lib.ecs.annotation.UpdateInGroup;
import priv.kgame.lib.ecs.extensions.system.EcsInitializeSystem;
import priv.kgame.lib.ecstest.component.add.component.ComponentA1;
import priv.kgame.lib.ecstest.component.add.component.ComponentA2;
import priv.kgame.lib.ecstest.component.add.group.SysGroupAddCompSpawn;

import java.util.Collection;
import java.util.List;

@UpdateInGroup(SysGroupAddCompSpawn.class)
@UpdateAfterSystem(systemTypes = SystemSpawnCustomOrder3.class)
public class SystemSpawnCustomOrder2 extends EcsInitializeSystem<ComponentA2> {

    @Override
    public boolean onInitialize(Entity entity, ComponentA2 data) {
        System.out.println(this.getClass().getSimpleName() +" update at: " + getWorld().getCurrentTime());
        ComponentA1 componentA1 = entity.getComponent(ComponentA1.class);
        componentA1.data += "o2";
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
