package priv.kgame.lib.ecs.tools;

import priv.kgame.lib.ecs.exception.InvalidEcsTypeException;
import priv.kgame.lib.ecs.exception.InvalidEcsTypeIndexException;

import java.util.HashMap;

public class TypeUtility {
    private TypeUtility() {}
    private static class TypeUtilityHandler {
        private static final TypeUtility INSTANCE = new TypeUtility();
    }

    public static TypeUtility getInstance() {
        return TypeUtilityHandler.INSTANCE;
    }

    private int count = 0;
    private final HashMap<Integer, Class<?>> indexTypeMap = new HashMap<>();
    private final HashMap<Class<?>, Integer> typeIndexMap = new HashMap<>();

    public int registerType(Class<?> type) {
        indexTypeMap.put(count, type);
        typeIndexMap.put(type, count);
        return count++;
    }

    public int getTypeIndex(Class<?> type) {
        Integer index = typeIndexMap.get(type);
        if (null == index) {
            throw new InvalidEcsTypeException(type);
        }
        return index;
    }

    public Class<?> getType(int index) {
        Class<?> type = indexTypeMap.get(index);
        if (null == type) {
            throw new InvalidEcsTypeIndexException(index);
        }
        return type;
    }

}
