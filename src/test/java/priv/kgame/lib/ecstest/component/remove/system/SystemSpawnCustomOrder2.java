package priv.kgame.lib.ecstest.component.remove.system;

import priv.kgame.lib.ecs.EcsComponent;
import priv.kgame.lib.ecs.Entity;
import priv.kgame.lib.ecs.annotation.UpdateInGroup;
import priv.kgame.lib.ecs.extensions.system.EcsInitializeSystem;
import priv.kgame.lib.ecstest.component.remove.component.ComponentRemove1;
import priv.kgame.lib.ecstest.component.remove.component.ComponentRemove2;
import priv.kgame.lib.ecstest.component.remove.group.SysGroupRemoveCompSpawn;

import java.util.Collection;
import java.util.List;

@UpdateInGroup(SysGroupRemoveCompSpawn.class)
public class SystemSpawnCustomOrder2 extends EcsInitializeSystem<ComponentRemove2> {

    @Override
    public boolean onInitialize(Entity entity, ComponentRemove2 data) {
        System.out.println(this.getClass().getSimpleName() +" update at: " + getWorld().getCurrentTime());
        ComponentRemove1 componentRemove1 = entity.getComponent(ComponentRemove1.class);
        componentRemove1.data += "o2";
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
