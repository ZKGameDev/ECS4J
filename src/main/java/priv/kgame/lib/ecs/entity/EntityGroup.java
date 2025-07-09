package priv.kgame.lib.ecs.entity;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import priv.kgame.lib.ecs.Disposable;
import priv.kgame.lib.ecs.EcsWorld;
import priv.kgame.lib.ecs.component.ComponentMatchType;
import priv.kgame.lib.ecs.component.ComponentTypeQuery;
import priv.kgame.lib.ecs.component.EcsComponent;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class EntityGroup implements Disposable {
    private static final Logger logger = LogManager.getLogger(EntityGroup.class);
    private final List<ComponentTypeQuery> requirementQuery = new ArrayList<>();
    private final List<EntityArchetype> matchingTypes = new ArrayList<>();
    private final EcsWorld world;
    public EntityGroup(EcsWorld world) {
        this.world = world;
    }

    public boolean isEmpty() {
       return matchingTypes.stream().noneMatch(
               entityArchetype -> entityArchetype.size() > 0);
    }

    public int length() {
        int result = 0;
        for (EntityArchetype entityArchetype : matchingTypes) {
            result += entityArchetype.size();
        }
        return result;
    }

    public List<Entity> getEntityList() {
        List<Entity> result = null;
        for (EntityArchetype entityArchetype : matchingTypes) {
            if (entityArchetype.size() > 0) {
                if (result == null) {
                    result = new ArrayList<>();
                }
                result.addAll(entityArchetype.getEntityList());
            }
        }
        return result == null ? Collections.emptyList() : result;
    }


    public <T extends EcsComponent> List<T> getComponentDataList(Class<T> tClass) {
        List<T> result = new ArrayList<>();
        ComponentMatchType<T> componentMatchType = ComponentMatchType.additive(world, tClass);
        for (EntityArchetype matchEntityArchetype : matchingTypes) {
            if (!matchEntityArchetype.getComponentTypes().contains(componentMatchType)) {
                logger.error("{} not exist in EntityGroup matchingTypes {}!", tClass.getSimpleName(), this);
                continue;
            }
            for(Entity entity : matchEntityArchetype.getEntityList()) {
                EcsComponent component = entity.getComponent(componentMatchType);
                if (null == component) {
                    logger.error("{} not exist in Entity {}!", tClass.getSimpleName(), entity);
                    continue;
                }
                result.add(tClass.cast(component));
            }
        }
        return result;
    }

    public boolean compareQuery(Collection<ComponentTypeQuery> queries) {
        for (ComponentTypeQuery query : queries) {
            if (!compareQuery(query)) {
                return false;
            }
        }
        return true;
    }

    public boolean compareQuery(ComponentTypeQuery[] queries) {
        for (ComponentTypeQuery query : queries) {
            if (!compareQuery(query)) {
                return false;
            }
        }
        return true;
    }

    public boolean compareQuery(ComponentTypeQuery query) {
        for (ComponentTypeQuery item : requirementQuery) {
            if (!item.equals(query)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void dispose() {
        matchingTypes.forEach(EntityArchetype::dispose);
        matchingTypes.clear();
        requirementQuery.forEach(ComponentTypeQuery::dispose);
        requirementQuery.clear();
    }

    public void addMatchType(EntityArchetype entityArchetype) {
        matchingTypes.add(entityArchetype);
    }

    public List<ComponentTypeQuery> getRequirementQuery() {
        return requirementQuery;
    }

    public void addRequirementQuery(ComponentTypeQuery[] componentTypeQuery) {
        requirementQuery.addAll(List.of(componentTypeQuery));
    }
}
