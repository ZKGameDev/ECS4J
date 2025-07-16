package priv.kgame.lib.ecstest.component.add.delay.system;

import priv.kgame.lib.ecs.Entity;
import priv.kgame.lib.ecs.annotation.UpdateInGroup;
import priv.kgame.lib.ecs.extensions.system.EcsUpdateSystemOne;
import priv.kgame.lib.ecstest.component.add.delay.component.ComponentDelayAdd1;
import priv.kgame.lib.ecstest.component.add.delay.component.ComponentDelayAdd3;
import priv.kgame.lib.ecstest.component.add.delay.group.SysGroupDelayAddComponentA;

@UpdateInGroup(SysGroupDelayAddComponentA.class)
public class SystemDelayAddComponent2 extends EcsUpdateSystemOne<ComponentDelayAdd3> {

    @Override
    protected void update(Entity entity, ComponentDelayAdd3 component) {
        System.out.println(this.getClass().getSimpleName() +" update at: " + getWorld().getCurrentTime());
        ComponentDelayAdd1 a1 = entity.getComponent(ComponentDelayAdd1.class);
        a1.data += "a2";
    }
}
