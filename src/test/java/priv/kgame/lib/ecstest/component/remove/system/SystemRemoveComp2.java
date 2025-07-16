package priv.kgame.lib.ecstest.component.remove.system;

import priv.kgame.lib.ecs.Entity;
import priv.kgame.lib.ecs.annotation.UpdateInGroup;
import priv.kgame.lib.ecs.extensions.system.EcsUpdateSystemOne;
import priv.kgame.lib.ecstest.component.remove.component.ComponentRemove1;
import priv.kgame.lib.ecstest.component.remove.component.ComponentRemove3;
import priv.kgame.lib.ecstest.component.remove.group.SysGroupRemoveCompA;

@UpdateInGroup(SysGroupRemoveCompA.class)
public class SystemRemoveComp2 extends EcsUpdateSystemOne<ComponentRemove3> {

    @Override
    protected void update(Entity entity, ComponentRemove3 component) {
        System.out.println(this.getClass().getSimpleName() +" update at: " + getWorld().getCurrentTime());
        ComponentRemove1 a1 = entity.getComponent(ComponentRemove1.class);
        a1.data += "a2";
    }
}
