package priv.kgame.lib.ecs.test.b.system;

import priv.kgame.lib.ecs.EcsWorld;
import priv.kgame.lib.ecs.test.b.group.SysGroupSpawnB;
import priv.kgame.lib.ecs.component.base.InitializedComponent;
import priv.kgame.lib.ecs.entity.Entity;
import priv.kgame.lib.ecs.system.annotation.UpdateInGroup;
import priv.kgame.lib.ecs.system.base.EcsInitializeSystem;
import priv.kgame.lib.ecs.test.b.component.ComponentB1;

@UpdateInGroup(SysGroupSpawnB.class)
public class SystemSpawnB1 extends EcsInitializeSystem<ComponentB1> {
    public SystemSpawnB1(EcsWorld ecsWorld) {
        super(ecsWorld);
    }

    @Override
    public boolean onInitialize(Entity entity, ComponentB1 data) {
        System.out.println(this.getClass().getSimpleName() +" update at: " + System.currentTimeMillis());
        getWorld().addComponent(entity, InitializedComponent.generate());
        return true;
    }
} 