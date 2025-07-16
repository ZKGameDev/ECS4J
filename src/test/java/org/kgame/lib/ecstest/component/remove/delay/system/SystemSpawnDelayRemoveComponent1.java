package org.kgame.lib.ecstest.component.remove.delay.system;

import org.kgame.lib.ecs.EcsComponent;
import org.kgame.lib.ecs.Entity;
import org.kgame.lib.ecs.annotation.UpdateInGroup;
import org.kgame.lib.ecs.extensions.system.EcsInitializeSystem;
import org.kgame.lib.ecstest.component.remove.delay.component.ComponentDelayRemove1;
import org.kgame.lib.ecstest.component.remove.delay.group.SysGroupDelayRemoveComponentSpawn;

import java.util.Collection;
import java.util.List;

@UpdateInGroup(SysGroupDelayRemoveComponentSpawn.class)
public class SystemSpawnDelayRemoveComponent1 extends EcsInitializeSystem<ComponentDelayRemove1> {

    @Override
    public boolean onInitialize(Entity entity, ComponentDelayRemove1 component) {
        System.out.println(this.getClass().getSimpleName() +" update at: " + getWorld().getCurrentTime());
        component.data += "o1";
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