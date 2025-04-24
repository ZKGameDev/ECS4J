package priv.kgame.lib.ecs.system.command;

public interface SystemCommand {
    SystemCommandType getType();
    void execute();
}
