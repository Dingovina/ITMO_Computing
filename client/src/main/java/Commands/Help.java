package Commands;

import SharedUtility.CommandType;
import Utility.Command;

import java.util.ArrayList;
import java.util.Scanner;

public class Help extends Command {
    public Help() {
        super("help", CommandType.HELP);
    }

    @Override
    public ArrayList<Object> getArgs(String arguments) {
        return null;
    }

    @Override
    public ArrayList<Object> getArgs(String arguments, Scanner file_input) {
        return null;
    }
}
