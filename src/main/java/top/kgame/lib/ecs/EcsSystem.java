package top.kgame.lib.ecs;

import top.kgame.lib.ecs.annotation.AlwaysUpdate;
import top.kgame.lib.ecs.annotation.UpdateIntervalTime;
import top.kgame.lib.ecs.command.EcsCommand;
import top.kgame.lib.ecs.command.EcsCommandScope;
import top.kgame.lib.ecs.command.EntityCommandBuffer;
import top.kgame.lib.ecs.core.ComponentTypeQuery;
import top.kgame.lib.ecs.core.EcsSystemManager;
import top.kgame.lib.ecs.core.EntityGroup;

import java.util.Collection;
import java.util.Collections;

public abstract class EcsSystem implements EcsCleanable {
    private EcsWorld ecsWorld;
    protected EcsSystemManager ecsSystemManager;
    private EntityCommandBuffer waitUpdateCommand;

    private boolean alwaysUpdateSystem = false;
    private boolean started = false;
    private boolean destroyed = false;
    private EntityGroup entityGroup;
    private long updateInterval = 0;
    private long nextUpdateTime = Long.MIN_VALUE;

    public void tryUpdate() {
        if (ecsWorld.getCurrentTime() >= nextUpdateTime) {
            if (shouldRunSystem()) {
                if (!started) {
                    started = true;
                    onStart();
                }
                onUpdate();
                waitUpdateCommand.playBack();
            } else if (started) {
                started = false;
                onStop();
            }
            nextUpdateTime = ecsWorld.getCurrentTime() + updateInterval;
        }
    }

    private boolean shouldRunSystem() {
        if (alwaysUpdateSystem) {
            return true;
        }
        if (entityGroup == null) {
            return false;
        }
        return !entityGroup.isEmpty();
    }

    @Override
    public void clean() {
        if (started) {
            started = false;
            onStop();
        }
        if (!destroyed) {
            onDestroy();
            destroyed = true;
        }
        waitUpdateCommand.clear();
    }

    public void init(EcsSystemManager systemManager) {
        this.ecsWorld = systemManager.getWorld();
        this.ecsSystemManager = systemManager;
        this.waitUpdateCommand = new EntityCommandBuffer();
        AlwaysUpdate alwaysUpdateAnno = this.getClass().getAnnotation(AlwaysUpdate.class);
        if (alwaysUpdateAnno != null) {
            alwaysUpdateSystem = true;
        }
        UpdateIntervalTime timeIntervalAnno = this.getClass().getAnnotation(UpdateIntervalTime.class);
        if (null != timeIntervalAnno) {
            this.updateInterval = (long) (timeIntervalAnno.interval() * 1000L);
        }
        destroyed = false;
        onInit();
    }

    protected void configEntityFilter(ComponentTypeQuery componentTypes) {
        if (entityGroup == null) {
            entityGroup = ecsWorld.getOrCreateEntityGroup(componentTypes);
        } else {
            if (!entityGroup.compareQuery(componentTypes)) {
                throw new UnsupportedOperationException("Repeatedly setting EntityGroup");
            }
        }
    }

    protected Collection<Entity> getAllMatchEntity() {
        if (entityGroup == null) {
            return Collections.emptyList();
        }
        return entityGroup.getEntityList();
    }

    public EcsWorld getWorld() {
        return ecsWorld;
    }

    public void addDelayCommand(EcsCommand command, EcsCommandScope level) {
        switch (level) {
            case SYSTEM -> this.waitUpdateCommand.addCommand(command);
            case SYSTEM_GROUP -> {
                if (this instanceof EcsSystemGroup) {
                    this.waitUpdateCommand.addCommand(command);
                } else if (this == ecsWorld.getCurrentSystemGroupClass()){
                    this.waitUpdateCommand.addCommand(command);
                } else {
                    ecsWorld.getCurrentSystemGroupClass().addDelayCommand(command, level);
                }
            }
            case WORLD -> ecsWorld.addDelayCommand(command);
        }
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    protected abstract void onInit();

    /**
     * 在停止状态下：
     * <p>如果alwaysUpdateSystem是true 或者 存在匹配的Entity时执行该方法<p/>
     * <p>该方法执行后System会被置为启动状态<p/>
     * <p>该方法在System的生命周期内有可能被多次执行<p/>
     */
    protected abstract void onStart();
    protected abstract void onUpdate();

    /**
     * 在启动状态下：
     * <p>如果alwaysUpdateSystem是false 且 不存在匹配的Entity时 执行该方法<p/>
     * <p>该方法执行后System会被置为停止状态<p/>
     * <p>该方法在System的生命周期内有可能被多次执行<p/>
     */
    protected abstract void onStop();

    /**
     * System销毁时执行该方法。
     * <p>该方法在System的生命周期内只会执行一次<p/>
     */
    protected abstract void onDestroy();
}