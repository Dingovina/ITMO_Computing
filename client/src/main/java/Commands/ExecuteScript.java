package Commands;

import SharedUtility.CommandType;
import Utility.Command;

import java.util.ArrayList;
import java.util.Scanner;

// TODO: Switch Command manager into FileExecution mode
public class ExecuteScript extends Command {
    public ExecuteScript() {

        super("execute_script", CommandType.EXECUTE_SCRIPT);
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
