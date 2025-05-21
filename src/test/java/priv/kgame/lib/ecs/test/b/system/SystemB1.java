package priv.kgame.lib.ecs.test.b.system;

import priv.kgame.lib.ecs.EcsWorld;
import priv.kgame.lib.ecs.test.b.group.SysGroupB;
import priv.kgame.lib.ecs.entity.Entity;
import priv.kgame.lib.ecs.system.annotation.UpdateInGroup;
import priv.kgame.lib.ecs.system.base.EcsUpdateSystemOne;
import priv.kgame.lib.ecs.test.b.component.ComponentB1;

@UpdateInGroup(SysGroupB.class)
public class SystemB1 extends EcsUpdateSystemOne<ComponentB1> {
    public SystemB1(EcsWorld ecsWorld) {
        super(ecsWorld);
    }

    @Override
    protected void update(Entity entity, ComponentB1 component) {
        System.out.println(this.getClass().getSimpleName() +" update at: " + System.currentTimeMillis());
    }
} 