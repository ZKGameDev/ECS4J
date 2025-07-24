package top.kgame.lib.ecstest.entity.remove.delay.system;

import top.kgame.lib.ecs.Entity;
import top.kgame.lib.ecs.EcsComponent;
import top.kgame.lib.ecs.annotation.UpdateInGroup;
import top.kgame.lib.ecs.extensions.system.EcsInitializeSystem;
import top.kgame.lib.ecstest.entity.remove.delay.component.EntityDelayRemove1;
import top.kgame.lib.ecstest.entity.remove.delay.group.SysGroupEntityDelayRemoveSpawn;

import java.util.Collection;
import java.util.Collections;

@UpdateInGroup(SysGroupEntityDelayRemoveSpawn.class)
public class SystemSpawnDelayEntityRemove1 extends EcsInitializeSystem<EntityDelayRemove1> {

    @Override
    public boolean onInitialize(Entity entity, EntityDelayRemove1 component) {
        System.out.println(this.getClass().getSimpleName() + " initialize at: " + getWorld().getCurrentTime());
        return true;
    }

    @Override
    protected SystemInitFinishSingle getInitFinishSingle() {
        return new SystemInitFinishSingle() {};
    }

    @Override
    public Collection<Class<? extends EcsComponent>> getExtraRequirementComponent() {
        return Collections.emptyList();
    }

    @Override
    public Collection<Class<? extends EcsComponent>> getExtraExcludeComponent() {
        return Collections.emptyList();
    }
} 