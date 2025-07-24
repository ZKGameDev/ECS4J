package top.kgame.lib.ecs.command;

import top.kgame.lib.ecs.EcsComponent;
import top.kgame.lib.ecs.Entity;

public class SystemCommandAddComponent implements EcsCommand {
    private final Entity entity;
    private final EcsComponent component;

    public SystemCommandAddComponent(Entity entity, EcsComponent component) {
        this.entity = entity;
        this.component = component;
    }

    @Override
    public void execute() {
        entity.addComponent(component);
    }
}
