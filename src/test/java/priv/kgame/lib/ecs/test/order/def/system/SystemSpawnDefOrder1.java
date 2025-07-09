package priv.kgame.lib.ecs.test.order.def.system;

import priv.kgame.lib.ecs.component.EcsComponent;
import priv.kgame.lib.ecs.component.base.InitializedComponent;
import priv.kgame.lib.ecs.entity.Entity;
import priv.kgame.lib.ecs.system.annotation.UpdateInGroup;
import priv.kgame.lib.ecs.system.base.EcsInitializeSystem;
import priv.kgame.lib.ecs.test.order.def.component.ComponentA1;
import priv.kgame.lib.ecs.test.order.def.group.SysGroupSpawn;

import java.util.Collection;
import java.util.List;

@UpdateInGroup(SysGroupSpawn.class)
public class SystemSpawnDefOrder1 extends EcsInitializeSystem<ComponentA1> {

    @Override
    public boolean onInitialize(Entity entity, ComponentA1 data) {
        System.out.println(this.getClass().getSimpleName() +" update at: " + System.currentTimeMillis());
        getWorld().addComponent(entity, InitializedComponent.generate());
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
