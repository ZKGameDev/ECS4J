package priv.kgame.lib.ecs.command;

public interface EcsCommand {
    SystemCommandType getType();
    void execute();
}
