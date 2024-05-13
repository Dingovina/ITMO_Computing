package Commands;

import SharedUtility.CommandType;
import Utility.Command;

import java.util.ArrayList;
import java.util.Scanner;

public class RemoveById extends Command {
    public RemoveById() {
        super("remove_by_id", CommandType.REMOVE_BY_ID);
    }

    @Override
    public ArrayList<Object> getArgs(String arguments){
        ArrayList<Object> args = new ArrayList<>();
        args.add(Long.parseLong(arguments));
        return args;
    }

    @Override
    public ArrayList<Object> getArgs(String arguments, Scanner file_input) {
        return getArgs(arguments);
    }
}
