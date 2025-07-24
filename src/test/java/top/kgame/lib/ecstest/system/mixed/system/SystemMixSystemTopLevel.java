package top.kgame.lib.ecstest.system.mixed.system;

import top.kgame.lib.ecs.Entity;
import top.kgame.lib.ecs.annotation.UpdateAfterSystem;
import top.kgame.lib.ecs.annotation.UpdateBeforeSystem;
import top.kgame.lib.ecs.extensions.system.EcsUpdateSystemOne;
import top.kgame.lib.ecstest.system.mixed.component.ComponentA1;
import top.kgame.lib.ecstest.system.mixed.component.ComponentA2;
import top.kgame.lib.ecstest.system.mixed.group.SysGroupMixSystemA;
import top.kgame.lib.ecstest.system.mixed.group.SysGroupMixSystemSpawn;

@UpdateAfterSystem(systemTypes = SysGroupMixSystemSpawn.class)
@UpdateBeforeSystem(systemTypes = SysGroupMixSystemA.class)
public class SystemMixSystemTopLevel extends EcsUpdateSystemOne<ComponentA2> {

    @Override
    protected void update(Entity entity, ComponentA2 component) {
        System.out.println(this.getClass().getSimpleName() +" update at: " + getWorld().getCurrentTime());
        ComponentA1 componentA1 = entity.getComponent(ComponentA1.class);
        component.data = componentA1.data;
        componentA1.data = "";
    }
}
