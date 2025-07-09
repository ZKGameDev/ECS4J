package priv.kgame.lib.ecs.system.base;

import priv.kgame.lib.ecs.component.ComponentType;
import priv.kgame.lib.ecs.component.EcsComponent;
import priv.kgame.lib.ecs.system.EcsSystem;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

abstract class EcsLogicSystem extends EcsSystem {
    private final List<ComponentType<?>> extraMatchComponent = new ArrayList<>();

    @Override
    protected void onInit() {
        processExtraComponent();
        List<ComponentType<?>> matchComponentTypes = new ArrayList<>();
        matchComponentTypes.addAll(getMatchComponent());
        matchComponentTypes.addAll(extraMatchComponent);
        configEntityFilter(matchComponentTypes);
    }

    public List<ComponentType<?>> getExtraMatchComponent() {
        return extraMatchComponent;
    }

    private void processExtraComponent() {
        Collection<Class<? extends EcsComponent>> requireComponent = getExtraRequirementComponent();
        if (requireComponent != null && !requireComponent.isEmpty()) {
            for (Class<? extends EcsComponent> clazz : requireComponent) {
                extraMatchComponent.add(ComponentType.additive(getWorld(), clazz));
            }
        }
        Collection<Class<? extends EcsComponent>> excludeComponent = getExtraExcludeComponent();
        if (excludeComponent != null && !excludeComponent.isEmpty()) {
            for (Class<? extends EcsComponent> clazz : excludeComponent) {
                extraMatchComponent.add(ComponentType.subtractive(getWorld(), clazz));
            }
        }
    }

    protected abstract Collection<ComponentType<?>> getMatchComponent();

    /**
     * 额外需要关注的Component类
     *
     * @return 关注的Component类的集合
     */
    public abstract Collection<Class<? extends EcsComponent>> getExtraRequirementComponent();

    /**
     * 额外需要排除的Component类
     *
     * @return 要排除的Component类的集合
     */
    public abstract Collection<Class<? extends EcsComponent>> getExtraExcludeComponent();
}
