package priv.kgame.lib.ecs.system;

import priv.kgame.lib.ecs.EcsWorld;
import priv.kgame.lib.ecs.component.ComponentType;
import priv.kgame.lib.ecs.component.EcsComponent;
import priv.kgame.lib.ecs.entity.Entity;
import priv.kgame.lib.ecs.system.command.*;

import java.util.concurrent.LinkedBlockingQueue;

public class SystemCommandBuffer {
    private final EcsWorld ecsWorld;
    private final LinkedBlockingQueue<SystemCommand> systemCommands = new LinkedBlockingQueue<>();
    public SystemCommandBuffer(EcsWorld ecsWorld) {
        this.ecsWorld = ecsWorld;
    }

    public void createEntity(int typeId, ComponentType<?> ... types) {
        systemCommands.offer(new SystemCommandCreateEntity(ecsWorld, typeId, types));
    }

    public void destroyEntity(Entity entity) {
        systemCommands.offer(new SystemCommandDestroyEntity(ecsWorld, entity));
    }

    public void addComponent(Entity entity, EcsComponent component) {
        systemCommands.offer(new SystemCommandAddComponent(ecsWorld, entity, component));
    }

    public void removeComponent(Entity entity, EcsComponent component) {
        systemCommands.offer(new SystemCommandRemoveComponent(ecsWorld, entity, component));
    }

    public void playBack() {
        while (!systemCommands.isEmpty()) {
            systemCommands.poll().execute();
        }
    }

    public void clear() {
        systemCommands.clear();
    }
}
