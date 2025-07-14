package priv.kgame.lib.ecs.system;

import priv.kgame.lib.ecs.Cleanable;
import priv.kgame.lib.ecs.EcsClassScanner;
import priv.kgame.lib.ecs.EcsWorld;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class EcsSystemManager implements Cleanable {
    private final EcsWorld world;
    private final List<EcsSystemGroup> systemGroups = new ArrayList<>();
    private Class<? extends EcsSystemGroup> currentSystemGroupClass;
    private EcsClassScanner ecsClassScanner;

    public EcsSystemManager(final EcsWorld world) {
        this.world = world;
    }

    public void init(EcsClassScanner ecsClassScanner) {
        this.ecsClassScanner = ecsClassScanner;
    }

    public void registerSystemGroup(Class<? extends EcsSystemGroup> clz) {
        EcsSystemGroup systemGroup = createSystem(clz);
        this.systemGroups.add(systemGroup);
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
        for (EcsSystemGroup systemGroup : systemGroups) {
            systemGroup.destroy();
        }
        this.currentSystemGroupClass = null;
        systemGroups.clear();
    }

    public void update() {
        for (EcsSystemGroup systemGroup : systemGroups) {
            this.currentSystemGroupClass = systemGroup.getClass();
            systemGroup.tryUpdate();

        }
    }

    public Class<? extends EcsSystemGroup> getCurrentSystemGroup() {
        return currentSystemGroupClass;
    }

    public Set<Class<? extends EcsSystem>> getChildSystemInGroup(EcsSystemGroup ecsSystemGroup) {
        return ecsClassScanner.getChildSystem(ecsSystemGroup.getClass());
    }
}
