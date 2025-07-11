package priv.kgame.lib.ecs.component;

import priv.kgame.lib.ecs.EcsWorld;

import java.lang.reflect.InvocationTargetException;
import java.util.Objects;

public class ComponentMatchParam<T extends EcsComponent> implements Comparable<ComponentMatchParam<?>>{
    private final ComponentAccessMode accessModeType;
    private int typeIndex = 0;
    private Class<T> type;

    public ComponentMatchParam() {
        this(ComponentAccessMode.READ_WRITE);
    }
    public ComponentMatchParam(ComponentAccessMode accessMode) {
        this.accessModeType = accessMode;
    }

    /**
     * 包含
     */
    public static <T extends EcsComponent> ComponentMatchParam<T> additive(EcsWorld ecsWorld, Class<T> type) {
        ComponentMatchParam<T> componentMatchType = new ComponentMatchParam<>();
        componentMatchType.typeIndex = ecsWorld.getComponentTypeIndex(type);
        componentMatchType.type = type;
        return componentMatchType;
    }

    /**
     * 不包含
     */
    public static <T extends EcsComponent> ComponentMatchParam<T> subtractive(EcsWorld ecsWorld, Class<T> type) {
        ComponentMatchParam<T> componentMatchType = new ComponentMatchParam<>(ComponentAccessMode.SUBTRACTIVE);
        componentMatchType.typeIndex = ecsWorld.getComponentTypeIndex(type);
        componentMatchType.type = type;
        return componentMatchType;
    }

    @Override
    public int compareTo(ComponentMatchParam<?> o) {
        return Integer.compare(typeIndex, o.typeIndex);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ComponentMatchParam<?> that = (ComponentMatchParam<?>) o;
        return typeIndex == that.typeIndex && accessModeType == that.accessModeType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(typeIndex);
    }

    public Class<T> getType() {
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