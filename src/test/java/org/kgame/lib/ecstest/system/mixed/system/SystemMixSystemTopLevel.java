package org.kgame.lib.ecstest.system.mixed.system;

import org.kgame.lib.ecs.Entity;
import org.kgame.lib.ecs.annotation.UpdateAfterSystem;
import org.kgame.lib.ecs.annotation.UpdateBeforeSystem;
import org.kgame.lib.ecs.extensions.system.EcsUpdateSystemOne;
import org.kgame.lib.ecstest.system.mixed.component.ComponentA1;
import org.kgame.lib.ecstest.system.mixed.component.ComponentA2;
import org.kgame.lib.ecstest.system.mixed.group.SysGroupMixSystemA;
import org.kgame.lib.ecstest.system.mixed.group.SysGroupMixSystemSpawn;

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
