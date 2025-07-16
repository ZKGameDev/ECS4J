package priv.kgame.lib.ecstest.component.add.delay.system;

import priv.kgame.lib.ecs.Entity;
import priv.kgame.lib.ecs.annotation.UpdateInGroup;
import priv.kgame.lib.ecs.extensions.system.EcsUpdateSystemTwo;
import priv.kgame.lib.ecstest.component.add.delay.component.ComponentDelayAdd1;
import priv.kgame.lib.ecstest.component.add.delay.component.ComponentDelayAdd3;
import priv.kgame.lib.ecstest.component.add.delay.group.SysGroupDelayAddComponentA;
import priv.kgame.lib.ecstest.component.add.delay.group.SysGroupDelayAddComponentB;

@UpdateInGroup(SysGroupDelayAddComponentB.class)
public class SystemDelayAddComponent3 extends EcsUpdateSystemTwo<ComponentDelayAdd1, ComponentDelayAdd3> {

    @Override
    protected void update(Entity entity, ComponentDelayAdd1 a1, ComponentDelayAdd3 a3) {
        System.out.println(this.getClass().getSimpleName() +" update at: " + getWorld().getCurrentTime());
        a1.data += "a3";
    }
}
