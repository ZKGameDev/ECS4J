package top.kgame.lib.ecstest.system.mixed.system;

import top.kgame.lib.ecs.Entity;
import top.kgame.lib.ecs.annotation.UpdateInGroup;
import top.kgame.lib.ecs.extensions.system.EcsUpdateSystemOne;
import top.kgame.lib.ecstest.system.mixed.component.ComponentA1;
import top.kgame.lib.ecstest.system.mixed.group.SysGroupMixSystemA;

@UpdateInGroup(SysGroupMixSystemA.class)
public class SystemMixSystem3 extends EcsUpdateSystemOne<ComponentA1> {

    @Override
    protected void update(Entity entity, ComponentA1 component) {
        System.out.println(this.getClass().getSimpleName() +" update at: " + getWorld().getCurrentTime());
        component.data += "a3";
    }
}
