package priv.kgame.lib.ecs.exception;

import java.io.Serial;

public class InvalidEcsTypeException extends RuntimeException{
    @Serial
    private static final long serialVersionUID = -4185391661062791482L;

    public InvalidEcsTypeException(Class<?> type) {
        super("Invalid type: " + type.getName());
    }
}
