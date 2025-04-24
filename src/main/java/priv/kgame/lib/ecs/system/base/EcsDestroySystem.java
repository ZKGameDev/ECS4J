package priv.kgame.lib.ecs.system.base;

import priv.kgame.lib.ecs.EcsWorld;
import priv.kgame.lib.ecs.component.ComponentType;
import priv.kgame.lib.ecs.component.EcsComponent;
import priv.kgame.lib.ecs.component.base.DespawningComponent;
import priv.kgame.lib.ecs.entity.Entity;
import priv.kgame.lib.ecs.entity.EntityGroup;
import priv.kgame.lib.ecs.system.EcsSystem;
import priv.kgame.lib.ecs.tools.EcsTools;

import java.lang.reflect.Type;
import java.util.List;

public abstract class EcsDestroySystem<T extends EcsComponent> extends EcsSystem {
    final private Class<T> genericClass;
    EntityGroup group;

    @SuppressWarnings("unchecked")
    public EcsDestroySystem(EcsWorld ecsWorld){
        super();
        Type[] parameterizedTypes = EcsTools.generateParameterizedType(this.getClass());
        genericClass = (Class<T>) parameterizedTypes[0];
//        init(ecsWorld);
    }

    @Override
    protected void onInit() {
        group = getOrAddEntityGroup(ComponentType.create(genericClass),
                ComponentType.create(DespawningComponent.class));
        super.setAlwaysUpdateSystem(true);
    }

    @Override
    protected void onUpdate() {
        List<Entity> entities = group.getEntityList();
        for (Entity entity : entities) {
            ComponentType<T> componentType = ComponentType.create(genericClass);
            entity.assertContainComponent(componentType);
            onEnd(entity, entity.getComponent(componentType));
        }
    }

    protected abstract void onEnd(Entity entity, T component);

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
