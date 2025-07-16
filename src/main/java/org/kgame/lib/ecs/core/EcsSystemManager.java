package org.kgame.lib.ecs.core;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.kgame.lib.ecs.EcsCleanable;
import org.kgame.lib.ecs.EcsSystem;
import org.kgame.lib.ecs.EcsSystemGroup;
import org.kgame.lib.ecs.EcsWorld;

import java.lang.reflect.InvocationTargetException;
import java.util.Set;

public class EcsSystemManager implements EcsCleanable {
    private static final Logger logger = LogManager.getLogger(EcsSystemManager.class);

    private final EcsWorld world;
    private final SortableSystemList topLevelSystems = new SortableSystemList();
    private EcsSystem currentTopSystem;
    private EcsClassScanner ecsClassScanner;

    public EcsSystemManager(final EcsWorld world) {
        this.world = world;
    }

    public void init(EcsClassScanner ecsClassScanner) {
        this.ecsClassScanner = ecsClassScanner;
        for (Class<? extends EcsSystem> systemClz : ecsClassScanner.getTopSystemClasses()) {
            EcsSystem system = createSystem(systemClz);
            topLevelSystems.addSystem(system);
        }
        topLevelSystems.tryReorderSystem();
        logger.info("{} order: {}", this.getClass().getSimpleName(), topLevelSystems);
    }

    public <T extends EcsSystem> T createSystem(Class<T> systemClass) {
        T system;
        try {
            system = systemClass.getConstructor().newInstance();
            system.init(this);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                 NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
        return system;
    }

    public EcsWorld getWorld() {
        return world;
    }

    @Override
    public void clean() {
        topLevelSystems.clean();
        this.currentTopSystem = null;
    }

    public void update() {
        for (EcsSystem systemGroup : topLevelSystems.getSortedSystem()) {
            this.currentTopSystem = systemGroup;
            systemGroup.tryUpdate();
        }
    }

    public EcsSystem getCurrentSystemGroup() {
        return currentTopSystem;
    }

    public Set<Class<? extends EcsSystem>> getSystemInGroup(EcsSystemGroup ecsSystemGroup) {
        return ecsClassScanner.getChildSystem(ecsSystemGroup.getClass());
    }
}
