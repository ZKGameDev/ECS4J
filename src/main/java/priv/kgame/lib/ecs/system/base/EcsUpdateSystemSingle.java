package priv.kgame.lib.ecs.system.base;

import priv.kgame.lib.ecs.system.EcsSystem;

public abstract class EcsUpdateSystemSingle extends EcsSystem {
    @Override
    protected void onInit() {
        super.setAlwaysUpdateSystem(true);
    }

    @Override
    protected void onStart() {

    }

    @Override
    protected void onUpdate() {
        update();
    }

    protected abstract void update();

    @Override
    protected void onStop() {

    }

    @Override
    protected void onDestroy() {

    }
}
