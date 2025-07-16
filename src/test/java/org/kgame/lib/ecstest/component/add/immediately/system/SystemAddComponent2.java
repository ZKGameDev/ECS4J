package org.kgame.lib.ecstest.component.add.immediately.system;

import org.kgame.lib.ecs.Entity;
import org.kgame.lib.ecs.annotation.UpdateInGroup;
import org.kgame.lib.ecs.extensions.system.EcsUpdateSystemOne;
import org.kgame.lib.ecstest.component.add.immediately.component.ComponentAdd1;
import org.kgame.lib.ecstest.component.add.immediately.component.ComponentAdd3;
import org.kgame.lib.ecstest.component.add.immediately.group.SysGroupAddComponentA;

@UpdateInGroup(SysGroupAddComponentA.class)
public class SystemAddComponent2 extends EcsUpdateSystemOne<ComponentAdd3> {

    @Override
    protected void update(Entity entity, ComponentAdd3 component) {
        System.out.println(this.getClass().getSimpleName() +" update at: " + getWorld().getCurrentTime());
        ComponentAdd1 a1 = entity.getComponent(ComponentAdd1.class);
        a1.data += "a2";
    }
}
