package top.kgame.lib.ecstest.component.remove.delay.system;

import top.kgame.lib.ecs.EcsComponent;
import top.kgame.lib.ecs.Entity;
import top.kgame.lib.ecs.annotation.UpdateInGroup;
import top.kgame.lib.ecs.extensions.system.EcsInitializeSystem;
import top.kgame.lib.ecstest.component.remove.delay.component.ComponentDelayRemove1;
import top.kgame.lib.ecstest.component.remove.delay.component.ComponentDelayRemove2;
import top.kgame.lib.ecstest.component.remove.delay.group.SysGroupDelayRemoveComponentSpawn;

import java.util.Collection;
import java.util.List;

@UpdateInGroup(SysGroupDelayRemoveComponentSpawn.class)
public class SystemSpawnDelayRemoveComponent2 extends EcsInitializeSystem<ComponentDelayRemove2> {

    @Override
    public boolean onInitialize(Entity entity, ComponentDelayRemove2 data) {
        System.out.println(this.getClass().getSimpleName() +" update at: " + getWorld().getCurrentTime());
        ComponentDelayRemove1 componentDelayRemove1 = entity.getComponent(ComponentDelayRemove1.class);
        componentDelayRemove1.data += "o2";
        return true;
    }

    @Override
    protected SystemInitFinishSingle getInitFinishSingle() {
        return new SystemInitFinishSingle() {};
    }

    @Override
    public Collection<Class<? extends EcsComponent>> getExtraRequirementComponent() {
        return List.of();
    }

    @Override
    public Collection<Class<? extends EcsComponent>> getExtraExcludeComponent() {
        return List.of();
    }
}