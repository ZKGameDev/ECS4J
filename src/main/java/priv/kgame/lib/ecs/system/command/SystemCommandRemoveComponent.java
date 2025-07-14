package priv.kgame.lib.ecs.system.command;

import priv.kgame.lib.ecs.EcsWorld;
import priv.kgame.lib.ecs.component.EcsComponent;
import priv.kgame.lib.ecs.entity.Entity;

public class SystemCommandRemoveComponent implements SystemCommand{
    private final EcsWorld ecsWorld;
    private final Entity entity;
    private final Class<? extends EcsComponent> componentCls;

    public SystemCommandRemoveComponent(EcsWorld ecsWorld, Entity entity, Class<? extends EcsComponent> componentCls) {
        this.ecsWorld = ecsWorld;
        this.entity = entity;
        this.componentCls = componentCls;
    }

    @Override
    public SystemCommandType getType() {
        return SystemCommandType.RemoveComponent;
    }

    @Override
    public void execute() {
        ecsWorld.removeComponent(entity, componentCls);
    }
}
