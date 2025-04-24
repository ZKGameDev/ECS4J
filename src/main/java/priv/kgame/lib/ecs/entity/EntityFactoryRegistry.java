package priv.kgame.lib.ecs.entity;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import priv.kgame.lib.ecs.EcsWorld;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class EntityFactoryRegistry {
    private static final Logger logger = LogManager.getLogger(EntityFactoryRegistry.class);
    private final Map<Integer, EntityFactory> entityFactoryTypeIdIndex = new HashMap<>();
    private final Map<Class<? extends EntityFactory>, EntityFactory> entityFactoryClassIndex = new HashMap<>();

    public void init() {
        EcsWorld.getEntityFactoryClass().forEach(entityFactoryClass -> {
            try {
                EntityFactory entityFactory = entityFactoryClass.getDeclaredConstructor().newInstance();
                registerEntityFactory(entityFactory);
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                     NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private void registerEntityFactory(EntityFactory entityFactory) {
        if (entityFactory.typeId() == 0) {
            logger.error("Invalid Factory, typeId must not be 0 !");
            return;
        }

        if (entityFactoryTypeIdIndex.containsKey(entityFactory.typeId())) {
            logger.error("EntityFactory already exist. name:{} typeId:{}", entityFactory.getClass().getName(), entityFactory.typeId());
            return;
        }

        entityFactoryTypeIdIndex.put(entityFactory.typeId(), entityFactory);
        entityFactoryClassIndex.put(entityFactory.getClass(), entityFactory);
    }

    public Entity createEntity(EcsWorld ecsWorld, int typeId) {
        EntityFactory entityFactory = entityFactoryTypeIdIndex.get(typeId);
        return entityFactory == null ? null : entityFactory.create(ecsWorld);
    }

    public Entity createEntity(EcsWorld ecsWorld, Class<? extends EntityFactory> klass) {
        EntityFactory entityFactory = entityFactoryClassIndex.get(klass);
        return entityFactory == null ? null : entityFactory.create(ecsWorld);
    }
}
