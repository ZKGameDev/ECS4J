package org.kgame.lib.ecs.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface UpdateIntervalTime {
    /**
     * 获取update更新间隔。
     * @return 以秒为单位的更新间隔。
     */
    float interval();
}
