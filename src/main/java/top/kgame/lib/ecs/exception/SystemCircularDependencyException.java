package top.kgame.lib.ecs.exception;

import top.kgame.lib.ecs.EcsSystem;

import java.io.Serial;
import java.util.Collection;

public class SystemCircularDependencyException extends RuntimeException{
    @Serial
    private static final long serialVersionUID = -3981779857000630542L;

    public SystemCircularDependencyException(Collection<EcsSystem> systemChain) {
        super(generateErrorMsg(systemChain));
    }

    private static String generateErrorMsg(Collection<EcsSystem> systems) {
        StringBuilder stringBuilder = new StringBuilder("The following systems form a circular dependency cycle (check their [UpdateBefore]/[UpdateAfter] attributes):\n");
        for (EcsSystem ecsSystem : systems) {
            stringBuilder.append(ecsSystem.getClass().getSimpleName()).append("\n");
        }
        return stringBuilder.toString();
    }
}
