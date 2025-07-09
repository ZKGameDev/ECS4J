package priv.kgame.lib.ecs.system;

import priv.kgame.lib.ecs.Disposable;
import priv.kgame.lib.ecs.EcsWorld;
import priv.kgame.lib.ecs.component.ComponentType;
import priv.kgame.lib.ecs.component.ComponentTypeQuery;
import priv.kgame.lib.ecs.entity.EntityGroup;

import java.util.ArrayList;
import java.util.List;

public abstract class EcsSystem implements Disposable {
    private EcsWorld ecsWorld;
    private SystemCommandBuffer waitUpdateCommand;

    private boolean alwaysUpdateSystem = false;
    private boolean started = false;
    private final List<EntityGroup> entityGroups = new ArrayList<>();
    private int updateFrames = 0;
    private int systemCreateOrder;
    private long updateInterval = 0;
    private long nextUpdateTime = -1000;

    public long getUpdateInterval() {
        return updateInterval;
    }

    public void setUpdateInterval(long updateInterval) {
        this.updateInterval = updateInterval;
    }

    public long getNextUpdateTime() {
        return nextUpdateTime;
    }

    public void setNextUpdateTime(long nextUpdateTime) {
        this.nextUpdateTime = nextUpdateTime;
    }

    public void tryUpdate() {
        if (ecsWorld.getCurrentTime() >= nextUpdateTime) {
            if (shouldRunSystem()) {
                if (!started) {
                    started = true;
                    onStart();
                }
                onUpdate();
                waitUpdateCommand.playBack();
                waitUpdateCommand.clear();
                updateFrames++;
            } else if (started) {
                started = false;
                onStop();
            }
            nextUpdateTime = ecsWorld.getCurrentTime() + updateInterval;
        }
    }

    boolean shouldRunSystem() {
        if (alwaysUpdateSystem) {
            return true;
        }
        if(!entityGroups.isEmpty()) {
            return checkEntityGroup();
        }
        return false;
    }

    private boolean checkEntityGroup() {
        for (EntityGroup entityGroup : entityGroups) {
            if (!entityGroup.isEmpty()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void dispose() {
        waitUpdateCommand.clear();
        entityGroups.forEach(EntityGroup::dispose);
        entityGroups.clear();
    }


    public void init(EcsWorld ecsWorld, int createdOrder) {
        this.ecsWorld = ecsWorld;
        this.systemCreateOrder = createdOrder;
        this.waitUpdateCommand = new SystemCommandBuffer(ecsWorld);
        onInit();
    }

    public void destroy() {
        if (started) {
            started = false;
            ecsWorld.destroySystem(this);
            onStop();
            onDestroy();
            dispose();
        }
    }

    protected EntityGroup getOrAddEntityGroup(List<ComponentType<?>> componentTypes) {
        return getOrAddEntityGroup(ecsWorld.createQuery(componentTypes.toArray(new ComponentType[0])));
    }
    protected EntityGroup getOrAddEntityGroup(ComponentType<?>... componentTypes) {
        return getOrAddEntityGroup(ecsWorld.createQuery(componentTypes));
    }

    protected EntityGroup getOrAddEntityGroup(ComponentTypeQuery componentTypes) {
        for (EntityGroup entityGroup : entityGroups) {
            if (entityGroup.compareQuery(componentTypes)) {
                return entityGroup;
            }
        }
        EntityGroup entityGroup = ecsWorld.createEntityGroup(new ComponentTypeQuery[]{componentTypes});
        entityGroups.add(entityGroup);
        return entityGroup;
    }
    protected EntityGroup getOrAddEntityGroup(ComponentTypeQuery... componentTypes) {
        for (EntityGroup entityGroup : entityGroups) {
            if (entityGroup.compareQuery(componentTypes)) {
                return entityGroup;
            }
        }
        EntityGroup entityGroup = ecsWorld.createEntityGroup(componentTypes);
        entityGroups.add(entityGroup);
        return entityGroup;
    }

    public void setAlwaysUpdateSystem(boolean alwaysUpdateSystem) {
        this.alwaysUpdateSystem = alwaysUpdateSystem;
    }

    public EcsWorld getWorld() {
        return ecsWorld;
    }

    public boolean isAlwaysUpdateSystem() {
        return alwaysUpdateSystem;
    }

    public int getSystemCreateOrder() {
        return systemCreateOrder;
    }

    public void setSystemCreateOrder(int systemCreateOrder) {
        this.systemCreateOrder = systemCreateOrder;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    protected abstract void onInit();
    protected abstract void onStart();
    protected abstract void onUpdate();
    protected abstract void onStop();
    protected abstract void onDestroy();
}
