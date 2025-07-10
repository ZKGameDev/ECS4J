package priv.kgame.lib.ecs.system.annotation;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Inherited
public @interface AlwaysUpdate {
}
