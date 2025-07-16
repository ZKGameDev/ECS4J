package org.kgame.lib.ecstest.system.order.def.system;

import org.kgame.lib.ecs.EcsComponent;
import org.kgame.lib.ecs.Entity;
import org.kgame.lib.ecs.annotation.UpdateInGroup;
import org.kgame.lib.ecs.extensions.system.EcsInitializeSystem;
import org.kgame.lib.ecstest.system.order.def.component.ComponentA1;
import org.kgame.lib.ecstest.system.order.def.group.SysGroupDefOrderSpawn;

import java.util.Collection;
import java.util.List;

@UpdateInGroup(SysGroupDefOrderSpawn.class)
public class SystemSpawnDefOrder1 extends EcsInitializeSystem<ComponentA1> {

    @Override
    public boolean onInitialize(Entity entity, ComponentA1 data) {
        System.out.println(this.getClass().getSimpleName() +" update at: " + getWorld().getCurrentTime());
        data.data = "o1";
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
