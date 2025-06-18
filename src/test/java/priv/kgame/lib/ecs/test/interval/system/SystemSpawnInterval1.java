package priv.kgame.lib.ecs.test.interval.system;

import priv.kgame.lib.ecs.EcsWorld;
import priv.kgame.lib.ecs.test.interval.group.SysGroupSpawnInterval;
import priv.kgame.lib.ecs.component.base.InitializedComponent;
import priv.kgame.lib.ecs.entity.Entity;
import priv.kgame.lib.ecs.system.annotation.UpdateInGroup;
import priv.kgame.lib.ecs.system.base.EcsInitializeSystem;
import priv.kgame.lib.ecs.test.interval.component.ComponentInterval1;

@UpdateInGroup(SysGroupSpawnInterval.class)
public class SystemSpawnInterval1 extends EcsInitializeSystem<ComponentInterval1> {
    public SystemSpawnInterval1(EcsWorld ecsWorld) {
        super(ecsWorld);
    }

    @Override
    public boolean onInitialize(Entity entity, ComponentInterval1 data) {
        System.out.println(this.getClass().getSimpleName() +" update at: " + System.currentTimeMillis());
        getWorld().addComponent(entity, InitializedComponent.generate());
        return true;
    }
} 