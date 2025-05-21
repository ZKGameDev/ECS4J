//package priv.kgame.lib.ecs.entity;
//
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//import priv.kgame.lib.ecs.EcsWorld;
//import priv.kgame.lib.ecs.exception.InvalidEcsEntityFactoryException;
//
//import java.lang.reflect.InvocationTargetException;
//import java.util.HashMap;
//import java.util.Map;
//
///**
// * 实体工厂注册表，用于管理和创建不同类型的实体。
// * 该类维护了一个实体工厂的注册表，可以通过类型ID或工厂类来创建对应的实体。
// * 每个实体工厂都需要实现 EntityFactory 接口，并通过 @EntityFactoryAttribute 注解标记。
// * 实体工厂在初始化时会被自动扫描并注册到注册表中。
// */
//public class EntityFactoryRegistry {
//    private static final Logger logger = LogManager.getLogger(EntityFactoryRegistry.class);
//    private final Map<Integer, EntityFactory> entityFactoryTypeIdIndex = new HashMap<>();
//    private final Map<Class<? extends EntityFactory>, EntityFactory> entityFactoryClassIndex = new HashMap<>();
//    private final EcsWorld ecsWorld;
//
//    public EntityFactoryRegistry(EcsWorld ecsWorld) {
//        this.ecsWorld = ecsWorld;
//    }
//
//    public void init() {
//        ecsWorld.getEntityFactoryClass().forEach(entityFactoryClass -> {
//            try {
//                EntityFactory entityFactory = entityFactoryClass.getDeclaredConstructor().newInstance();
//                registerEntityFactory(entityFactory);
//            } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
//                     NoSuchMethodException e) {
//                throw new RuntimeException(e);
//            }
//        });
//    }
//
//    private void registerEntityFactory(EntityFactory entityFactory) {
//        if (entityFactory.typeId() == 0) {
//            throw new InvalidEcsEntityFactoryException("Invalid Factory, typeId must not be 0 !");
//        }
//
//        if (entityFactoryTypeIdIndex.containsKey(entityFactory.typeId())) {
//            logger.error("EntityFactory already exist. name:{} typeId:{}", entityFactory.getClass().getName(), entityFactory.typeId());
//            throw new InvalidEcsEntityFactoryException("EntityFactory already exist. name:" + entityFactory.getClass().getName() + " typeId:" + entityFactory.typeId());
//        }
//
//        entityFactoryTypeIdIndex.put(entityFactory.typeId(), entityFactory);
//        entityFactoryClassIndex.put(entityFactory.getClass(), entityFactory);
//    }
//
//    public Entity createEntity(int typeId) {
//        EntityFactory entityFactory = entityFactoryTypeIdIndex.get(typeId);
//        return entityFactory == null ? null : entityFactory.create(ecsWorld);
//    }
//
//    public Entity createEntity(Class<? extends EntityFactory> klass) {
//        EntityFactory entityFactory = entityFactoryClassIndex.get(klass);
//        return entityFactory == null ? null : entityFactory.create(ecsWorld);
//    }
//}
