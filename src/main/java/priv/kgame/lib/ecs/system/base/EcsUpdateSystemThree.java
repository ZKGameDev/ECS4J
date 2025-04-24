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

public abstract class EcsUpdateSystemThree <T1 extends EcsComponent,
        T2 extends EcsComponent, T3 extends EcsComponent> extends EcsSystem {

    private final Class<T1> componentClass1;
    private final Class<T2> componentClass2;
    private final Class<T3> componentClass3;

    protected List<ComponentType<?>> extraRequirementComponent = new ArrayList<>();
    private EntityGroup entityGroup;

    @SuppressWarnings("unchecked")
    public EcsUpdateSystemThree(EcsWorld ecsWorld) {
        super();
        Type[] parameterizedTypes = EcsTools.generateParameterizedType(this.getClass());
        componentClass1 = (Class<T1>) parameterizedTypes[0];
        componentClass2 = (Class<T2>) parameterizedTypes[1];
        componentClass3 = (Class<T3>) parameterizedTypes[2];
//        init(ecsWorld);
    }

    @Override
    protected void onInit() {
        List<ComponentType<?>> componentTypes = new ArrayList<>();
        componentTypes.add(ComponentType.create(componentClass1));
        componentTypes.add(ComponentType.create(componentClass2));
        componentTypes.add(ComponentType.create(componentClass3));
        if (!extraRequirementComponent.isEmpty()) {
            componentTypes.addAll(extraRequirementComponent);
        }
        componentTypes.add(ComponentType.subtractive(DespawningComponent.class));
        componentTypes.add(ComponentType.create(InitializedComponent.class));
        entityGroup = getOrAddEntityGroup(componentTypes);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void onUpdate() {
        for (Entity entity : entityGroup.getEntityList()) {
            ComponentType<T1> componentType1 = ComponentType.create(componentClass1);
            ComponentType<T2> componentType2 = ComponentType.create(componentClass2);
            ComponentType<T3> componentType3 = ComponentType.create(componentClass3);
            entity.assertContainComponent(componentType1);
            entity.assertContainComponent(componentType2);
            update(entity, entity.getComponent(componentType1), entity.getComponent(componentType2)
                    , entity.getComponent(componentType3));
        }
    }

    protected abstract void update(Entity entity, T1 component1, T2 component2, T3 component3);

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
