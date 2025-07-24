package top.kgame.lib.ecs.command;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import top.kgame.lib.ecs.EcsWorld;
import top.kgame.lib.ecs.Entity;

import java.util.function.Consumer;

public class SystemCommandCreateEntity implements EcsCommand {
    private static final Logger logger = LogManager.getLogger(SystemCommandCreateEntity.class);
    private final EcsWorld ecsWorld;
    private final int typeId;
    private final Consumer<Entity> successCallback;

    public SystemCommandCreateEntity(EcsWorld ecsWorld, int typeId, Consumer<Entity> successCallback) {
        this.ecsWorld = ecsWorld;
        this.typeId = typeId;
        this.successCallback = successCallback;
    }

    @Override
    public void execute() {
        Entity entity = ecsWorld.createEntity(typeId);
        successCallback.accept(entity);
        logger.debug("SystemCommandCreateEntity {}", entity);
    }
}
