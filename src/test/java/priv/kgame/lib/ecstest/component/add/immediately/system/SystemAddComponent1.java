package priv.kgame.lib.ecstest.component.add.immediately.system;

import priv.kgame.lib.ecs.Entity;
import priv.kgame.lib.ecs.annotation.UpdateInGroup;
import priv.kgame.lib.ecs.extensions.system.EcsUpdateSystemOne;
import priv.kgame.lib.ecstest.component.add.immediately.component.ComponentAdd1;
import priv.kgame.lib.ecstest.component.add.immediately.component.ComponentAdd3;
import priv.kgame.lib.ecstest.component.add.immediately.group.SysGroupAddComponentA;

@UpdateInGroup(SysGroupAddComponentA.class)
public class SystemAddComponent1 extends EcsUpdateSystemOne<ComponentAdd1> {

    @Override
    protected void update(Entity entity, ComponentAdd1 component) {
        System.out.println(this.getClass().getSimpleName() +" update at: " + getWorld().getCurrentTime());
        component.data += "a1";

        if (component.addComponent3Time == getWorld().getCurrentTime()) {
            entity.addComponent(new ComponentAdd3());
        }
    }
}
