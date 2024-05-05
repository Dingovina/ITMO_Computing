package Commands;

import SharedUtility.CommandType;
import Utility.Command;

import java.util.ArrayList;
import java.util.Scanner;

public class CountByDescription extends Command {
    public CountByDescription() {
        super("count_by_description", CommandType.COUNT_BY_DESCRIPTION);
    }
    @Override
    public ArrayList<Object> getArgs(String arguments) {
        ArrayList<Object> args = new ArrayList<>();
        args.add(arguments);

        return args;
    }

    @Override
    public ArrayList<Object> getArgs(String arguments, Scanner file_input) {
        return getArgs(arguments);
    }
}
