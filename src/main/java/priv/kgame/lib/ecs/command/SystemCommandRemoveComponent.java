package priv.kgame.lib.ecs.command;

import priv.kgame.lib.ecs.EcsComponent;
import priv.kgame.lib.ecs.Entity;

public class SystemCommandRemoveComponent implements EcsCommand {
    private final Entity entity;
    private final Class<? extends EcsComponent> componentCls;

    public SystemCommandRemoveComponent(Entity entity, Class<? extends EcsComponent> componentCls) {
        this.entity = entity;
        this.componentCls = componentCls;
    }

    @Override
    public void execute() {
        entity.removeComponent(componentCls);
    }
}
