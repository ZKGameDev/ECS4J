package priv.kgame.lib.ecs.exception;

import priv.kgame.lib.ecs.EcsSystem;
import priv.kgame.lib.ecs.EcsSystemGroup;

import java.io.Serial;

public class InvalidUpdateInGroupTypeException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = -4361318954743504981L;

    public InvalidUpdateInGroupTypeException(Class<? extends EcsSystem> clazz, Class<? extends EcsSystemGroup> groupClass) {
        super("The system " + clazz.getName() + " has an invalid update in group " + groupClass.getName());
    }
}
