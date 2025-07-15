package priv.kgame.lib.ecs.command;

import java.util.LinkedList;


public class EntityCommandBuffer {
    private final LinkedList<EcsCommand> ecsCommands = new LinkedList<>();

    public enum Level {
        SYSTEM,
        SYSTEM_GROUP,
        WORLD,
    }

    public void addCommand(EcsCommand command) {
        ecsCommands.add(command);
    }

    public void playBack() {
        while (!ecsCommands.isEmpty()) {
            ecsCommands.poll().execute();
        }
    }

    public void clear() {
        ecsCommands.clear();
    }
}
