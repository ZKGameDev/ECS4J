package priv.kgame.lib.ecs;

import java.io.Closeable;

public interface Disposable extends Closeable {
    @Override
    default void close() {
        dispose();
    }

    void dispose();
}
