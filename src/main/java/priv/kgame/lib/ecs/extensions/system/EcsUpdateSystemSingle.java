package priv.kgame.lib.ecs.extensions.system;

import priv.kgame.lib.ecs.EcsSystem;
import priv.kgame.lib.ecs.annotation.AlwaysUpdate;

/**
 * 单例更新系统基类
 * <p>
 * 该类继承自EcsSystem，用于实现单例模式的更新系统。
 * 特点：
 * 1. 系统在初始化时会被设置为始终更新（alwaysUpdateSystem = true）
 * 2. 每次世界更新时只会执行一次update()方法
 */
@AlwaysUpdate
public abstract class EcsUpdateSystemSingle extends EcsSystem {

    @Override
    protected void onInit() {
    }

    @Override
    protected void onStart() {
    }

    @Override
    protected void onStop() {

    }

    @Override
    protected void onDestroy() {

    }
}
