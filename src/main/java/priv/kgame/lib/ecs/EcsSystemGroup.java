package priv.kgame.lib.ecs;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import priv.kgame.lib.ecs.annotation.AlwaysUpdate;
import priv.kgame.lib.ecs.core.SortableSystemList;

import java.util.ArrayList;
import java.util.List;

@AlwaysUpdate
public abstract class EcsSystemGroup extends EcsSystem {
    private static final Logger logger = LogManager.getLogger(EcsSystemGroup.class);

    private final SortableSystemList sortableSystemList = new SortableSystemList();
    protected final List<EcsSystem> systemsToRemove = new ArrayList<>();

    @Override
    public void onInit() {
        for (Class<? extends EcsSystem> childSystemClass : super.ecsSystemManager.getSystemInGroup(this)) {
            addSystemToUpdateList(childSystemClass);
        }
        sortableSystemList.tryReorderSystem();
        logger.info("{} order: {}", this.getClass().getSimpleName(), sortableSystemList);
    }

    private void addSystemToUpdateList(Class<? extends EcsSystem> childSystemClass) {
        EcsSystem system = super.ecsSystemManager.createSystem(childSystemClass);
        if (null == system) {
            logger.error("addSystemToUpdateList failed! reason: generate EcsSystem failed! systemClass:{}", childSystemClass.getSimpleName());
            return;
        }
        sortableSystemList.addSystem(system);
    }

    public void removeSystem(EcsSystem system) {
        systemsToRemove.add(system);
    }

    @Override
    protected void onUpdate() {
        for (EcsSystem system : sortableSystemList.getSortedSystem()) {
            system.tryUpdate();
        }
        destroyRemovedSystem();
    }

    private void destroyRemovedSystem() {
        if (!systemsToRemove.isEmpty()) {
            for(EcsSystem system : systemsToRemove) {
                sortableSystemList.removeSystem(system);
                system.clean();
            }
            systemsToRemove.clear();
        }
    }

    @Override
    protected void onDestroy() {
        destroyRemovedSystem();
        sortableSystemList.clean();
    }
}
