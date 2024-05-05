package Commands;

import SharedUtility.CommandType;
import Utility.Command;

import java.util.ArrayList;
import java.util.Scanner;

public class Info extends Command {
    public Info() {
        super("info", CommandType.INFO);
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
