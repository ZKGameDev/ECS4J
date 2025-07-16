package priv.kgame.lib.ecstest.component.add.immediately.system;

import priv.kgame.lib.ecs.Entity;
import priv.kgame.lib.ecs.annotation.UpdateInGroup;
import priv.kgame.lib.ecs.extensions.system.EcsUpdateSystemOne;
import priv.kgame.lib.ecstest.component.add.immediately.component.ComponentAdd1;
import priv.kgame.lib.ecstest.component.add.immediately.component.ComponentAdd2;
import priv.kgame.lib.ecstest.component.add.immediately.group.SysGroupAddComponentA;

@UpdateInGroup(SysGroupAddComponentA.class)
public class SystemAddComponent4 extends EcsUpdateSystemOne<ComponentAdd2> {

    @Override
    protected void update(Entity entity, ComponentAdd2 component) {
        System.out.println(this.getClass().getSimpleName() +" update at: " + getWorld().getCurrentTime());
        ComponentAdd1 componentAdd1 = entity.getComponent(ComponentAdd1.class);
        component.data = componentAdd1.data;
        componentAdd1.data = "";
    }
}
