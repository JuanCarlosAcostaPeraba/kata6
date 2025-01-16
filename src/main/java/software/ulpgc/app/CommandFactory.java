package software.ulpgc.app;

import software.ulpgc.control.Command;
import software.ulpgc.control.Input;
import software.ulpgc.control.Output;

import java.util.HashMap;
import java.util.Map;

public class CommandFactory {
    public interface CommandBuilder {
        Command build(Input input, Output output);
    }

    private final Map<String, CommandBuilder> commandBuilderMap;

    public CommandFactory() {
        commandBuilderMap = new HashMap<>();
    }

    public Command get(String key, Input input, Output output) {
        return commandBuilderMap.get(key).build(input, output);
    }

    public CommandFactory register(String key, CommandBuilder commandBuilder) {
        commandBuilderMap.put(key, commandBuilder);
        return this;
    }
}
