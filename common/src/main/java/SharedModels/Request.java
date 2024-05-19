package SharedModels;

import SharedUtility.CommandType;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

public record Request(CommandType commandType, ArrayList<Object> arguments, String username) implements Serializable {

    public boolean isExit() {
        return commandType == CommandType.EXIT;
    }

    public boolean isValid() {
        return !Objects.isNull(commandType);
    }
}
