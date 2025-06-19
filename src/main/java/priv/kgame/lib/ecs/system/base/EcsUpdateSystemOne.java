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

public abstract class EcsUpdateSystemOne<T extends EcsComponent> extends EcsSystem {
    private final Class<T> entityClass;
    private EntityGroup entityGroup;
    protected List<ComponentType<?>> extraRequirementComponent = new ArrayList<>();

    @SuppressWarnings("unchecked")
    public EcsUpdateSystemOne(EcsWorld ecsWorld) {
        super(ecsWorld);
        Type[] parameterizedTypes = EcsTools.generateParameterizedType(this.getClass());
        entityClass = (Class<T>) parameterizedTypes[0];
    }

    @Override
    protected void onInit() {
        List<ComponentType<?>> typeList = new ArrayList<>();
        typeList.add(ComponentType.additive(getWorld(), entityClass));
        if (!extraRequirementComponent.isEmpty()) {
            typeList.addAll(extraRequirementComponent);
        }
        typeList.add(ComponentType.subtractive(getWorld(), DespawningComponent.class));
        typeList.add(ComponentType.additive(getWorld(), InitializedComponent.class));
        entityGroup = getOrAddEntityGroup(typeList);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void onUpdate() {
        List<Entity> entities = entityGroup.getEntityList();
        for (Entity entity : entities) {
            ComponentType<T> componentType = ComponentType.additive(getWorld(), entityClass);
            entity.assertContainComponent(componentType);
            update(entity, entity.getComponent(componentType));
        }
    }

    protected abstract void update(Entity entity, T component);

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
