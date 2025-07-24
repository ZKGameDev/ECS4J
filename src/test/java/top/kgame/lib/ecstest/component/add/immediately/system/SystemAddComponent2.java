package top.kgame.lib.ecstest.component.add.immediately.system;

import top.kgame.lib.ecs.Entity;
import top.kgame.lib.ecs.annotation.UpdateInGroup;
import top.kgame.lib.ecs.extensions.system.EcsUpdateSystemOne;
import top.kgame.lib.ecstest.component.add.immediately.component.ComponentAdd1;
import top.kgame.lib.ecstest.component.add.immediately.component.ComponentAdd3;
import top.kgame.lib.ecstest.component.add.immediately.group.SysGroupAddComponentA;

@UpdateInGroup(SysGroupAddComponentA.class)
public class SystemAddComponent2 extends EcsUpdateSystemOne<ComponentAdd3> {

    @Override
    protected void update(Entity entity, ComponentAdd3 component) {
        System.out.println(this.getClass().getSimpleName() +" update at: " + getWorld().getCurrentTime());
        ComponentAdd1 a1 = entity.getComponent(ComponentAdd1.class);
        a1.data += "a2";
    }
}
