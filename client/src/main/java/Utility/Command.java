package Utility;

import SharedUtility.CommandType;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;

public abstract class Command implements Serializable {
    private String name;
    private CommandType commandType;

    public Command(String name, CommandType commandType){

        this.name = name;
        this.commandType = commandType;
    }

    public String getName() {
        return name;
    }

    public abstract ArrayList<Object> getArgs(String arguments);
    public abstract ArrayList<Object> getArgs(String arguments, Scanner file_input);

    public CommandType getCommandType() {
        return commandType;
    }
}
