package priv.kgame.lib.ecs.component;

import priv.kgame.lib.ecs.EcsWorld;

import java.lang.reflect.InvocationTargetException;
import java.util.Objects;

public class ComponentType<T extends EcsComponent> implements Comparable<ComponentType<?>>{
    private final ComponentAccessMode accessModeType;
    private int typeIndex = 0;
    private Class<T> type;

    public ComponentType() {
        this(ComponentAccessMode.READ_WRITE);
    }
    public ComponentType(ComponentAccessMode accessMode) {
        this.accessModeType = accessMode;
    }

    public static <T extends EcsComponent> ComponentType<T> additive(EcsWorld ecsWorld, Class<T> type) {
        ComponentType<T> componentType = new ComponentType<>();
        componentType.typeIndex = ecsWorld.getComponentTypeIndex(type);
        componentType.type = type;
        return componentType;
    }

    public static <T extends EcsComponent> ComponentType<T> subtractive(EcsWorld ecsWorld, Class<T> type) {
        ComponentType<T> componentType = new ComponentType<>(ComponentAccessMode.SUBTRACTIVE);
        componentType.typeIndex = ecsWorld.getComponentTypeIndex(type);
        componentType.type = type;
        return componentType;
    }

    @Override
    public int compareTo(ComponentType<?> o) {
        return Integer.compare(typeIndex, o.typeIndex);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ComponentType<?> that = (ComponentType<?>) o;
        return typeIndex == that.typeIndex && accessModeType == that.accessModeType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(typeIndex);
    }

    public Class<? extends EcsComponent> getType() {
        return type;
    }

    public ComponentAccessMode getAccessModeType() {
        return accessModeType;
    }

    /**
     * 通过type的默认构造函数生成对应的Component实例
     * @return type对应的Component实例
     * @throws RuntimeException 当type没有默认构造函数时抛出
     */
    public T generateComponentByDefaultConstructor() {
        try {
            return type.getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException("createEntity failed! component "
                    + type.getName() + " don't has default constructor", e);
        }
    }

    public int getTypeIndex() {
        return typeIndex;
    }
}
