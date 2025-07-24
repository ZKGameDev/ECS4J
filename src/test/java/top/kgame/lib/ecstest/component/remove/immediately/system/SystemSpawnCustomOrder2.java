package top.kgame.lib.ecstest.component.remove.immediately.system;

import top.kgame.lib.ecs.EcsComponent;
import top.kgame.lib.ecs.Entity;
import top.kgame.lib.ecs.annotation.UpdateInGroup;
import top.kgame.lib.ecs.extensions.system.EcsInitializeSystem;
import top.kgame.lib.ecstest.component.remove.immediately.component.ComponentRemove1;
import top.kgame.lib.ecstest.component.remove.immediately.component.ComponentRemove2;
import top.kgame.lib.ecstest.component.remove.immediately.group.SysGroupRemoveCompSpawn;

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
