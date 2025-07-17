package org.kgame.lib.ecstest.entity.add.delay.system;

import org.kgame.lib.ecs.Entity;
import org.kgame.lib.ecs.EcsComponent;
import org.kgame.lib.ecs.annotation.UpdateInGroup;
import org.kgame.lib.ecs.command.SystemCommandCreateEntity;
import org.kgame.lib.ecs.extensions.system.EcsInitializeSystem;
import org.kgame.lib.ecstest.entity.add.delay.EcsEntityDelayAddTest;
import org.kgame.lib.ecstest.entity.add.delay.component.EntityDelayAdd1;
import org.kgame.lib.ecstest.entity.add.delay.group.SysGroupEntityDelayAddSpawn;

import java.util.Collection;
import java.util.Collections;

@UpdateInGroup(SysGroupEntityDelayAddSpawn.class)
public class SystemSpawnDelayEntityAdd1 extends EcsInitializeSystem<EntityDelayAdd1> {

    @Override
    public boolean onInitialize(Entity entity, EntityDelayAdd1 component) {
        System.out.println(this.getClass().getSimpleName() + " initialize at: " + getWorld().getCurrentTime());
        component.spawnTime = getWorld().getCurrentTime();
        EcsEntityDelayAddTest.LogicContext context = getWorld().getContext();
        if (context.entity12 == null) {
            addDelayCommand(new SystemCommandCreateEntity(getWorld(), 12, it -> {context.entity12 = it;}), context.level);
            context.entity12CreateTime = getWorld().getCurrentTime();
        }
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