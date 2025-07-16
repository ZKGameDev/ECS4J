package org.kgame.lib.ecs.exception;

import java.io.Serial;

public class InvalidEcsTypeIndexException extends RuntimeException{

    @Serial
    private static final long serialVersionUID = -856015255930732360L;

    public InvalidEcsTypeIndexException(int index) {
        super("Invalid Type Index: " + index);
    }
}
