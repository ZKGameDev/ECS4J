package priv.kgame.lib.ecs.system.command;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import priv.kgame.lib.ecs.EcsWorld;
import priv.kgame.lib.ecs.component.ComponentMatchParam;
import priv.kgame.lib.ecs.component.EcsComponent;
import priv.kgame.lib.ecs.entity.Entity;

import java.util.Arrays;
import java.util.Collection;

public class SystemCommandCreateEntity implements SystemCommand {
    private static final Logger logger = LogManager.getLogger(SystemCommandCreateEntity.class);
    private final EcsWorld ecsWorld;
    private final Collection<Class<? extends EcsComponent>> types;
    private final int typeId;

    public SystemCommandCreateEntity(EcsWorld ecsWorld, int typeId, Collection<Class<? extends EcsComponent>> types) {
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
        Entity entity = ecsWorld.createEntity(typeId, types);
        logger.debug("SystemCommandCreateEntity {}", entity);
    }
}
