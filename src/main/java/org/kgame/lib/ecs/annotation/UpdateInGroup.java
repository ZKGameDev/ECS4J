package org.kgame.lib.ecs.annotation;

import org.kgame.lib.ecs.EcsSystemGroup;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface UpdateInGroup {
    Class<? extends EcsSystemGroup> value();
}
