package priv.kgame.lib.ecs.command;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import priv.kgame.lib.ecs.EcsWorld;
import priv.kgame.lib.ecs.Entity;

public class SystemCommandCreateEntity implements EcsCommand {
    private static final Logger logger = LogManager.getLogger(SystemCommandCreateEntity.class);
    private final EcsWorld ecsWorld;
    private final int typeId;

    public SystemCommandCreateEntity(EcsWorld ecsWorld, int typeId) {
        this.ecsWorld = ecsWorld;
        this.typeId = typeId;
    }

    @Override
    public void execute() {
        Entity entity = ecsWorld.createEntity(typeId);
        logger.debug("SystemCommandCreateEntity {}", entity);
    }
}
