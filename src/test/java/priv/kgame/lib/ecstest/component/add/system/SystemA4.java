package priv.kgame.lib.ecstest.component.add.system;

import priv.kgame.lib.ecs.Entity;
import priv.kgame.lib.ecs.annotation.UpdateInGroup;
import priv.kgame.lib.ecs.extensions.system.EcsUpdateSystemOne;
import priv.kgame.lib.ecstest.component.add.component.ComponentA1;
import priv.kgame.lib.ecstest.component.add.component.ComponentA2;
import priv.kgame.lib.ecstest.component.add.group.SysGroupAddCompA;

@UpdateInGroup(SysGroupAddCompA.class)
public class SystemA4 extends EcsUpdateSystemOne<ComponentA2> {

    @Override
    protected void update(Entity entity, ComponentA2 component) {
        System.out.println(this.getClass().getSimpleName() +" update at: " + getWorld().getCurrentTime());
        ComponentA1 componentA1 = entity.getComponent(ComponentA1.class);
        component.data = componentA1.data;
        componentA1.data = "";
    }
}
