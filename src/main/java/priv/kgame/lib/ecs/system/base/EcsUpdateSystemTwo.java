package priv.kgame.lib.ecs.system.base;

import priv.kgame.lib.ecs.EcsWorld;
import priv.kgame.lib.ecs.component.ComponentType;
import priv.kgame.lib.ecs.component.EcsComponent;
import priv.kgame.lib.ecs.component.base.DespawningComponent;
import priv.kgame.lib.ecs.component.base.InitializedComponent;
import priv.kgame.lib.ecs.entity.Entity;
import priv.kgame.lib.ecs.entity.EntityGroup;
import priv.kgame.lib.ecs.system.EcsSystem;
import priv.kgame.lib.ecs.tools.EcsTools;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public abstract class EcsUpdateSystemTwo<T1 extends EcsComponent, T2 extends EcsComponent> extends EcsSystem {
    private final Class<T1> componentClass1;
    private final Class<T2> componentClass2;
    protected List<ComponentType<?>> extraRequirementComponent = new ArrayList<>();
    private EntityGroup entityGroup;

    @SuppressWarnings("unchecked")
    public EcsUpdateSystemTwo(EcsWorld ecsWorld) {
        super(ecsWorld);
        Type[] parameterizedTypes = EcsTools.generateParameterizedType(this.getClass());
        componentClass1 = (Class<T1>) parameterizedTypes[0];
        componentClass2 = (Class<T2>) parameterizedTypes[1];
    }

    @Override
    protected void onInit() {
        List<ComponentType<?>> componentTypes = new ArrayList<>();
        componentTypes.add(ComponentType.additive(getWorld(), componentClass1));
        componentTypes.add(ComponentType.additive(getWorld(), componentClass2));
        if (!extraRequirementComponent.isEmpty()) {
            componentTypes.addAll(extraRequirementComponent);
        }
        componentTypes.add(ComponentType.subtractive(getWorld(), DespawningComponent.class));
        componentTypes.add(ComponentType.additive(getWorld(), InitializedComponent.class));
        entityGroup = getOrAddEntityGroup(componentTypes);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void onUpdate() {
        for (Entity entity : entityGroup.getEntityList()) {
            ComponentType<T1> componentType1 = ComponentType.additive(getWorld(), componentClass1);
            ComponentType<T2> componentType2 = ComponentType.additive(getWorld(), componentClass2);
            entity.assertContainComponent(componentType1);
            entity.assertContainComponent(componentType2);
            update(entity, entity.getComponent(componentType1), entity.getComponent(componentType2));
        }
    }

    protected abstract void update(Entity entity, T1 component, T2 component1);

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
