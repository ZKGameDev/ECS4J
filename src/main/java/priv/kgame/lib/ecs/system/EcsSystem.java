package priv.kgame.lib.ecs.system;

import priv.kgame.lib.ecs.Disposable;
import priv.kgame.lib.ecs.EcsWorld;
import priv.kgame.lib.ecs.component.ComponentMatchType;
import priv.kgame.lib.ecs.component.ComponentTypeQuery;
import priv.kgame.lib.ecs.entity.Entity;
import priv.kgame.lib.ecs.entity.EntityGroup;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

public abstract class EcsSystem implements Disposable {
    private EcsWorld ecsWorld;
    private SystemCommandBuffer waitUpdateCommand;

    private boolean alwaysUpdateSystem = false;
    private boolean started = false;
    private boolean destroyed = false;
    private EntityGroup entityGroup;
    private long updateInterval = 0;
    private long nextUpdateTime = -1000;

    public void setUpdateInterval(long updateInterval) {
        this.updateInterval = updateInterval;
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

        if (entityGroup == null) {
            return false;
        }
        return !entityGroup.isEmpty();
    }

    @Override
    public void dispose() {
        waitUpdateCommand.clear();
        if (entityGroup != null) {
            entityGroup.dispose();
        }
    }


    public void init(EcsWorld ecsWorld) {
        this.ecsWorld = ecsWorld;
        this.waitUpdateCommand = new SystemCommandBuffer(ecsWorld);
        onInit();
    }

    public void destroy() {
        if (started) {
            started = false;
            onStop();
        }
        if (!destroyed) {
            onDestroy();
            dispose();
            destroyed = true;
        }
    }

    protected void configEntityFilter(List<ComponentMatchType<?>> componentMatchTypes) {
        configEntityFilter(ecsWorld.createQuery(componentMatchTypes.toArray(new ComponentMatchType[0])));
    }
    protected void configEntityFilter(ComponentMatchType<?>... componentMatchTypes) {
        configEntityFilter(ecsWorld.createQuery(componentMatchTypes));
    }

    protected void configEntityFilter(ComponentTypeQuery componentTypes) {
        if (entityGroup == null) {
            entityGroup = ecsWorld.getOrCreateEntityGroup(new ComponentTypeQuery[]{componentTypes});
        } else {
            if (!entityGroup.compareQuery(componentTypes)) {
                throw new UnsupportedOperationException("Repeatedly setting EntityGroup");
            }
        }
    }

    public Collection<Entity> getAllMatchEntity() {
        if (entityGroup == null) {
            return Collections.emptyList();
        }
        return entityGroup.getEntityList();
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

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    protected abstract void onInit();

    /**
     * 在停止状态下：
     * 如果alwaysUpdateSystem是true 或者 存在匹配的Entity时执行该方法
     * 该方法执行后System会被设为启动状态。
     * 该方法在System的生命周期内有可能被多次执行
     */
    protected abstract void onStart();
    protected abstract void onUpdate();

    /**
     * 在启动状态下：
     * 如果alwaysUpdateSystem是false 且 不存在匹配的Entity时 执行该方法。
     * 该方法执行后System会被职位停止状态。
     * 该方法在System的生命周期内有可能被多次执行
     */
    protected abstract void onStop();

    /**
     * System销毁时执行该方法。
     * 该方法在System的生命周期内只会执行一次。
     */
    protected abstract void onDestroy();
}