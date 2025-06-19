package priv.kgame.lib.ecs.test.order.def.system;

import priv.kgame.lib.ecs.EcsWorld;
import priv.kgame.lib.ecs.component.EcsComponent;
import priv.kgame.lib.ecs.entity.Entity;
import priv.kgame.lib.ecs.system.annotation.UpdateInGroup;
import priv.kgame.lib.ecs.system.base.EcsInitializeCustomSystem;
import priv.kgame.lib.ecs.test.order.def.component.ComponentA1;
import priv.kgame.lib.ecs.test.order.def.group.SysGroupSpawn;

@UpdateInGroup(SysGroupSpawn.class)
public class SystemSpawnDefOrder4 extends EcsInitializeCustomSystem<ComponentA1, SystemSpawnDefOrder4.Initialized> {
    public static class Initialized implements EcsComponent {}

    public SystemSpawnDefOrder4(EcsWorld ecsWorld) {
        super(ecsWorld);
    }

    @Override
    public boolean onInitialize(Entity entity, ComponentA1 data) {
        System.out.println(this.getClass().getSimpleName() + " update at: " + System.currentTimeMillis());
        data.data += "o4";
        return true;
    }
}
