package Utility;

import SharedModels.Response;
import SharedUtility.CommandType;

import java.util.ArrayList;

public abstract class Command {
    private String name;
    private CommandType commandType;

    public Command(String name, CommandType commandType){
        this.name = name;
        this.commandType = commandType;
    }

    abstract public Response execute(ArrayList<Object> args);
    public String getName() {
        return name;
    }
    public CommandType getCommandType(){
        return commandType;
    }
}
