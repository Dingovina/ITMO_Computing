package SharedModels;

import SharedUtility.CommandType;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

public class Request implements Serializable {
    private CommandType commandType;
    private ArrayList<Object> arguments;

    public Request(CommandType commandType, ArrayList<Object> arguments){
        this.commandType = commandType;
        this.arguments = arguments;
    }

    public boolean isExit(){
        return commandType == CommandType.EXIT;
    }
    public boolean isValid(){
        return !Objects.isNull(commandType);
    }

    public CommandType getCommandType() {
        return commandType;
    }

    public ArrayList<Object> getArguments() {
        return arguments;
    }
}
