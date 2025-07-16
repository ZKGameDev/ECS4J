package org.kgame.lib.ecs.tools;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class EcsTools {
    public static Type[] generateParameterizedType(Class<?> kclass) {
        Type genType = kclass.getGenericSuperclass();
        if (genType instanceof ParameterizedType parameterizedType) {
            return parameterizedType.getActualTypeArguments();
        } else {
            throw new RuntimeException("EcsInitializeSystem Constructor execute failed! reason:GenericSuperclass not instanceof ParameterizedType");
        }
    }
}
