package priv.kgame.lib.ecstest.component.add.immediately.system;

import priv.kgame.lib.ecs.EcsComponent;
import priv.kgame.lib.ecs.Entity;
import priv.kgame.lib.ecs.annotation.UpdateAfterSystem;
import priv.kgame.lib.ecs.annotation.UpdateInGroup;
import priv.kgame.lib.ecs.extensions.system.EcsInitializeSystem;
import priv.kgame.lib.ecstest.component.add.immediately.component.ComponentAdd1;
import priv.kgame.lib.ecstest.component.add.immediately.component.ComponentAdd2;
import priv.kgame.lib.ecstest.component.add.immediately.group.SysGroupAddComponentSpawn;

import java.util.Collection;
import java.util.List;

@UpdateInGroup(SysGroupAddComponentSpawn.class)
@UpdateAfterSystem(systemTypes = SystemSpawnAddComponent3.class)
public class SystemSpawnAddComponent2 extends EcsInitializeSystem<ComponentAdd2> {

    @Override
    public boolean onInitialize(Entity entity, ComponentAdd2 data) {
        System.out.println(this.getClass().getSimpleName() +" update at: " + getWorld().getCurrentTime());
        ComponentAdd1 componentAdd1 = entity.getComponent(ComponentAdd1.class);
        componentAdd1.data += "o2";
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
