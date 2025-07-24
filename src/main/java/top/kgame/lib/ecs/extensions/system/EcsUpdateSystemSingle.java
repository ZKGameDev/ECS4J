package top.kgame.lib.ecs.extensions.system;

import top.kgame.lib.ecs.EcsSystem;
import top.kgame.lib.ecs.annotation.AlwaysUpdate;

/**
 * 单例更新系统基类
 * <p>
 * 用于实现单例模式的更新系统，每次世界更新时只会执行一次update()方法。
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
