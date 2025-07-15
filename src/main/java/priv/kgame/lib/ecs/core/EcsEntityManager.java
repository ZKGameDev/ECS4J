package priv.kgame.lib.ecs.core;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import priv.kgame.lib.ecs.EcsCleanable;
import priv.kgame.lib.ecs.EcsComponent;
import priv.kgame.lib.ecs.EcsWorld;
import priv.kgame.lib.ecs.Entity;
import priv.kgame.lib.ecs.exception.InvalidEcsEntityFactoryException;
import priv.kgame.lib.ecs.extensions.entity.EntityFactory;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class EcsEntityManager implements EcsCleanable {
    private static final Logger logger = LogManager.getLogger(EcsEntityManager.class);

    private final EcsWorld world;
    private final List<EntityArchetype> entityArchetypes = new ArrayList<>();
    private final List<EntityGroup> systemNeedEntityGroups = new ArrayList<>();
    private final Map<Integer, Entity> entityIndex = new HashMap<>();
    private final EntityFactoryIndex entityFactoryIndex = new EntityFactoryIndex();

    private int entitiesNextIndex = 1;

    public EcsEntityManager(final EcsWorld world) {
        this.world = world;
    }

    @Override
    public void clean() {
        entityArchetypes.clear();
        systemNeedEntityGroups.clear();
        entityIndex.clear();
        entityFactoryIndex.clear();
    }

    public void init(EcsClassScanner ecsClassScanner) {
        ecsClassScanner.getEntityFactoryClass().forEach(entityFactoryClass -> {
            try {
                EntityFactory entityFactory = entityFactoryClass.getDeclaredConstructor().newInstance();
                entityFactoryIndex.registerEntityFactory(entityFactory);
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                     NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public Entity getEntity(int index) {
        return entityIndex.get(index);
    }

    public Collection<Entity> getAllEntity() {
        return entityIndex.values();
    }

    private static class EntityFactoryIndex {
        private final Map<Integer, EntityFactory> entityFactoryTypeIdIndex = new HashMap<>();
        private final Map<Class<? extends EntityFactory>, EntityFactory> entityFactoryClassIndex = new HashMap<>();

        private void registerEntityFactory(EntityFactory entityFactory) {
            if (entityFactory.typeId() == 0) {
                throw new InvalidEcsEntityFactoryException("Invalid Factory, typeId must not be 0 !");
            }

            if (entityFactoryTypeIdIndex.containsKey(entityFactory.typeId())) {
                logger.error("EntityFactory already exist. name:{} typeId:{}", entityFactory.getClass().getName(), entityFactory.typeId());
                throw new InvalidEcsEntityFactoryException("EntityFactory has same typeId! name:" + entityFactory.getClass().getName() + " typeId:" + entityFactory.typeId());
            }

            entityFactoryTypeIdIndex.put(entityFactory.typeId(), entityFactory);
            entityFactoryClassIndex.put(entityFactory.getClass(), entityFactory);
        }

        private EntityFactory get(int typeId) {
            return entityFactoryTypeIdIndex.get(typeId);
        }

        private EntityFactory get(Class<? extends EntityFactory> clazz) {
            return entityFactoryClassIndex.get(clazz);
        }

        private void clear() {
            entityFactoryTypeIdIndex.clear();
            entityFactoryClassIndex.clear();
        }
    }


    public EntityFactory getEntityFactory(int factoryType) {
        return entityFactoryIndex.get(factoryType);
    }
    public EntityFactory getEntityFactory(Class<? extends EntityFactory> clazz) {
        return entityFactoryIndex.get(clazz);
    }

    public Entity createEntityInstance(int typeId) {
        return createEntityInstance(typeId, Collections.emptyList());
    }

    public Entity createEntityInstance(int typeId, Collection<? extends EcsComponent> components) {
        Entity entity = new Entity(this, entitiesNextIndex++, typeId, components);
        entity.init();
        entityIndex.put(entity.getIndex(), entity);
        return entity;
    }

    public EntityArchetype getOrCreateArchetype(Collection<Class<? extends EcsComponent>> types) {
        EntityArchetype existArchetype = getExistingArchetype(types);
        if (existArchetype != null) {
            return existArchetype;
        }
        return createArchetype(types);
    }

    private EntityArchetype getExistingArchetype(Collection<Class<? extends EcsComponent>> types) {
        if (null == types || types.isEmpty()) {
            return null;
        }
        for (EntityArchetype entityArchetype : entityArchetypes) {
            if (entityArchetype.isSame(types)) {
                return entityArchetype;
            }
        }
        return null;
    }

    private EntityArchetype createArchetype(Collection<Class<? extends EcsComponent>> types) {
        EntityArchetype entityArchetype = new EntityArchetype();
        for (Class<? extends EcsComponent> componentMatchType : types) {
            entityArchetype.addComponent(componentMatchType);
        }
        entityArchetypes.add(entityArchetype);
        systemNeedEntityGroups.forEach(entityGroup -> entityGroup.addArchetypeIfMatching(entityArchetype));
        return entityArchetype;
    }

    private boolean notExistEntity(Entity entity) {
        if (entitiesNextIndex < entity.getIndex()) {
            return true;
        }
        return !entityIndex.containsKey(entity.getIndex());
    }

    public EntityGroup getOrCreateEntityGroup(ComponentTypeQuery componentTypeQuery) {
        EntityGroup entityGroup = null;
        for (EntityGroup item : systemNeedEntityGroups) {
            if (item.compareQuery(componentTypeQuery)) {
                entityGroup = item;
            }
        }
        if (entityGroup == null) {
            EntityGroup newEntityGroup = new EntityGroup();
            newEntityGroup.addRequirementQuery(componentTypeQuery);
            entityArchetypes.forEach(newEntityGroup::addArchetypeIfMatching);
            systemNeedEntityGroups.add(newEntityGroup);
            return newEntityGroup;
        } else {
            return entityGroup;
        }
    }

    public EcsWorld getWorld() {
        return world;
    }

    public void destroyEntity(Entity entity) {
        if (notExistEntity(entity)) {
            logger.warn("destroy entity failed! reason: entity not exist. index:{}", entity.getIndex());
            return;
        }
        if (entityIndex.remove(entity.getIndex()) != null) {
            entity.clean();
        }
    }
}
