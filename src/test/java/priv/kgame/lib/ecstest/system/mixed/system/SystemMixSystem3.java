package priv.kgame.lib.ecstest.system.mixed.system;

import priv.kgame.lib.ecs.Entity;
import priv.kgame.lib.ecs.annotation.UpdateInGroup;
import priv.kgame.lib.ecs.extensions.system.EcsUpdateSystemOne;
import priv.kgame.lib.ecstest.system.mixed.component.ComponentA1;
import priv.kgame.lib.ecstest.system.mixed.group.SysGroupMixSystemA;

@UpdateInGroup(SysGroupMixSystemA.class)
public class SystemMixSystem3 extends EcsUpdateSystemOne<ComponentA1> {

    @Override
    protected void update(Entity entity, ComponentA1 component) {
        System.out.println(this.getClass().getSimpleName() +" update at: " + getWorld().getCurrentTime());
        component.data += "a3";
    }
}
