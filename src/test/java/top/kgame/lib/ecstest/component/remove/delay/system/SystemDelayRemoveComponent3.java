package top.kgame.lib.ecstest.component.remove.delay.system;

import top.kgame.lib.ecs.Entity;
import top.kgame.lib.ecs.annotation.UpdateInGroup;
import top.kgame.lib.ecs.extensions.system.EcsUpdateSystemTwo;
import top.kgame.lib.ecstest.component.remove.delay.component.ComponentDelayRemove1;
import top.kgame.lib.ecstest.component.remove.delay.component.ComponentDelayRemove3;
import top.kgame.lib.ecstest.component.remove.delay.group.SysGroupDelayRemoveComponentB;

@UpdateInGroup(SysGroupDelayRemoveComponentB.class)
public class SystemDelayRemoveComponent3 extends EcsUpdateSystemTwo<ComponentDelayRemove1, ComponentDelayRemove3> {

    @Override
    protected void update(Entity entity, ComponentDelayRemove1 r1, ComponentDelayRemove3 r3) {
        System.out.println(this.getClass().getSimpleName() +" update at: " + getWorld().getCurrentTime());
        r1.data += "r3";
    }
} 