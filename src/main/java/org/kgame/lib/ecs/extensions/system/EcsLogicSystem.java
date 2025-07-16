package org.kgame.lib.ecs.extensions.system;

import org.kgame.lib.ecs.core.ComponentMatchParam;
import org.kgame.lib.ecs.core.ComponentTypeQuery;
import org.kgame.lib.ecs.EcsComponent;
import org.kgame.lib.ecs.EcsSystem;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

abstract class EcsLogicSystem extends EcsSystem {
    private final List<ComponentMatchParam<?>> extraMatchComponent = new ArrayList<>();

    @Override
    protected void onInit() {
        processExtraComponent();
        List<ComponentMatchParam<?>> matchComponentMatchTypes = new ArrayList<>();
        matchComponentMatchTypes.addAll(getMatchComponent());
        matchComponentMatchTypes.addAll(extraMatchComponent);
        configEntityFilter(ComponentTypeQuery.generate(matchComponentMatchTypes));
    }

    public List<ComponentMatchParam<?>> getExtraMatchComponent() {
        return extraMatchComponent;
    }

    private void processExtraComponent() {
        Collection<Class<? extends EcsComponent>> requireComponent = getExtraRequirementComponent();
        if (requireComponent != null && !requireComponent.isEmpty()) {
            for (Class<? extends EcsComponent> clazz : requireComponent) {
                extraMatchComponent.add(ComponentMatchParam.additive(getWorld(), clazz));
            }
        }
        Collection<Class<? extends EcsComponent>> excludeComponent = getExtraExcludeComponent();
        if (excludeComponent != null && !excludeComponent.isEmpty()) {
            for (Class<? extends EcsComponent> clazz : excludeComponent) {
                extraMatchComponent.add(ComponentMatchParam.subtractive(getWorld(), clazz));
            }
        }
    }

    protected abstract Collection<ComponentMatchParam<?>> getMatchComponent();

    /**
     * 额外需要关注的Component类
     */
    public abstract Collection<Class<? extends EcsComponent>> getExtraRequirementComponent();

    /**
     * 额外需要排除的Component类
     */
    public abstract Collection<Class<? extends EcsComponent>> getExtraExcludeComponent();
}
