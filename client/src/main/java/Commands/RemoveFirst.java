package Commands;

import SharedUtility.CommandType;
import Utility.Command;

import java.util.ArrayList;
import java.util.Scanner;

public class RemoveFirst extends Command {
    public RemoveFirst() {
        super("remove_first", CommandType.REMOVE_FIRST);
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
