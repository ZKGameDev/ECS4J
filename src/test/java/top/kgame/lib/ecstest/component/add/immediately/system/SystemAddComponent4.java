package top.kgame.lib.ecstest.component.add.immediately.system;

import top.kgame.lib.ecs.Entity;
import top.kgame.lib.ecs.annotation.UpdateInGroup;
import top.kgame.lib.ecs.extensions.system.EcsUpdateSystemOne;
import top.kgame.lib.ecstest.component.add.immediately.component.ComponentAdd1;
import top.kgame.lib.ecstest.component.add.immediately.component.ComponentAdd2;
import top.kgame.lib.ecstest.component.add.immediately.group.SysGroupAddComponentA;

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
