package priv.kgame.lib.ecs.test.a.system;

import priv.kgame.lib.ecs.EcsWorld;
import priv.kgame.lib.ecs.test.a.group.SysGroupA;
import priv.kgame.lib.ecs.entity.Entity;
import priv.kgame.lib.ecs.system.annotation.UpdateInGroup;
import priv.kgame.lib.ecs.system.base.EcsUpdateSystemOne;
import priv.kgame.lib.ecs.test.a.component.ComponentA1;

@UpdateInGroup(SysGroupA.class)
public class SystemA1 extends EcsUpdateSystemOne<ComponentA1> {
    public SystemA1(EcsWorld ecsWorld) {
        super(ecsWorld);
    }

    @Override
    protected void update(Entity entity, ComponentA1 component) {
        System.out.println(this.getClass().getSimpleName() +" update at: " + System.currentTimeMillis());
    }
}
