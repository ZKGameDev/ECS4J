package priv.kgame.lib.ecs.test.order.def.system;

import priv.kgame.lib.ecs.EcsWorld;
import priv.kgame.lib.ecs.component.base.InitializedComponent;
import priv.kgame.lib.ecs.entity.Entity;
import priv.kgame.lib.ecs.system.annotation.UpdateInGroup;
import priv.kgame.lib.ecs.system.base.EcsInitializeSystem;
import priv.kgame.lib.ecs.test.order.def.component.ComponentA1;
import priv.kgame.lib.ecs.test.order.def.group.SysGroupSpawn;

/**
 * 该System理应不会执行到，因为SystemSpawnDefOrder1已经将InitializedComponent放入了entity。
 * 如果该System执行到说明EcsInitializeSystem的语义被破坏。
 */
@UpdateInGroup(SysGroupSpawn.class)
public class SystemSpawnDefOrder3 extends EcsInitializeSystem<ComponentA1> {
    public SystemSpawnDefOrder3(EcsWorld ecsWorld) {
        super(ecsWorld);
    }

    @Override
    public boolean onInitialize(Entity entity, ComponentA1 data) {
        System.out.println(this.getClass().getSimpleName() +" update at: " + System.currentTimeMillis());
        getWorld().addComponent(entity, InitializedComponent.generate());
        data.data += "o3";
        return true;
    }
}
