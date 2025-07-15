package priv.kgame.lib.ecs.annotation;

import priv.kgame.lib.ecs.extensions.system.EcsSystemGroup;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface UpdateInGroup {
    Class<? extends EcsSystemGroup> value();
}
