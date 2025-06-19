package priv.kgame.lib.ecs.test.order.def.system;

import priv.kgame.lib.ecs.EcsWorld;
import priv.kgame.lib.ecs.test.order.def.group.SysGroupSpawn;
import priv.kgame.lib.ecs.component.base.InitializedComponent;
import priv.kgame.lib.ecs.entity.Entity;
import priv.kgame.lib.ecs.system.annotation.UpdateInGroup;
import priv.kgame.lib.ecs.system.base.EcsInitializeSystem;
import priv.kgame.lib.ecs.test.order.def.component.ComponentA1;

@UpdateInGroup(SysGroupSpawn.class)
public class SystemSpawnDefOrder1 extends EcsInitializeSystem<ComponentA1> {
    public SystemSpawnDefOrder1(EcsWorld ecsWorld) {
        super(ecsWorld);
    }

    @Override
    public boolean onInitialize(Entity entity, ComponentA1 data) {
        System.out.println(this.getClass().getSimpleName() +" update at: " + System.currentTimeMillis());
        getWorld().addComponent(entity, InitializedComponent.generate());
        data.data = "o1";
        return true;
    }
}
