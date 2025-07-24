package top.kgame.lib.ecstest.component.add.immediately.system;

import top.kgame.lib.ecs.Entity;
import top.kgame.lib.ecs.annotation.UpdateInGroup;
import top.kgame.lib.ecs.extensions.system.EcsUpdateSystemTwo;
import top.kgame.lib.ecstest.component.add.immediately.component.ComponentAdd1;
import top.kgame.lib.ecstest.component.add.immediately.component.ComponentAdd3;
import top.kgame.lib.ecstest.component.add.immediately.group.SysGroupAddComponentA;

@UpdateInGroup(SysGroupAddComponentA.class)
public class SystemAddComponent3 extends EcsUpdateSystemTwo<ComponentAdd1, ComponentAdd3> {

    @Override
    protected void update(Entity entity, ComponentAdd1 a1, ComponentAdd3 a3) {
        System.out.println(this.getClass().getSimpleName() +" update at: " + getWorld().getCurrentTime());
        a1.data += "a3";
    }
}
