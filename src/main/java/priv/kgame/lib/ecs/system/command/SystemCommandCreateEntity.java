package priv.kgame.lib.ecs.system.command;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import priv.kgame.lib.ecs.EcsWorld;
import priv.kgame.lib.ecs.component.ComponentType;
import priv.kgame.lib.ecs.entity.Entity;

import java.util.Arrays;

public class SystemCommandCreateEntity implements SystemCommand{
    private static final Logger logger = LogManager.getLogger(SystemCommandCreateEntity.class);
    private final EcsWorld ecsWorld;
    private final ComponentType<?>[] types;
    private final int typeId;
    public SystemCommandCreateEntity(EcsWorld ecsWorld, int typeId, ComponentType<?>[] types) {
        this.ecsWorld = ecsWorld;
        this.types = types;
        this.typeId = typeId;
    }

    @Override
    public SystemCommandType getType() {
        return SystemCommandType.CreateEntity;
    }

    @Override
    public void execute() {
        Entity entity = ecsWorld.createEntity(typeId, Arrays.asList(types));
        logger.debug("SystemCommandCreateEntity {}", entity);
    }
}
