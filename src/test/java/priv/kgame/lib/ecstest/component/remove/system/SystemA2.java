package priv.kgame.lib.ecstest.component.remove.system;

import priv.kgame.lib.ecs.entity.Entity;
import priv.kgame.lib.ecs.system.annotation.UpdateInGroup;
import priv.kgame.lib.ecs.system.base.EcsUpdateSystemOne;
import priv.kgame.lib.ecstest.component.remove.component.ComponentA1;
import priv.kgame.lib.ecstest.component.remove.component.ComponentA3;
import priv.kgame.lib.ecstest.component.remove.group.SysGroupA;

@UpdateInGroup(SysGroupA.class)
public class SystemA2 extends EcsUpdateSystemOne<ComponentA3> {

    @Override
    protected void update(Entity entity, ComponentA3 component) {
        System.out.println(this.getClass().getSimpleName() +" update at: " + getWorld().getCurrentTime());
        ComponentA1 a1 = entity.getComponent(ComponentA1.class);
        a1.data += "a2";
    }
}
