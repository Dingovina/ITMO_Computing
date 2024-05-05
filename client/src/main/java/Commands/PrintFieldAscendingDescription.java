package Commands;

import SharedUtility.CommandType;
import Utility.Command;

import java.util.ArrayList;
import java.util.Scanner;

public class PrintFieldAscendingDescription extends Command {
    public PrintFieldAscendingDescription() {
        super(
                "print_field_ascending_description",
                CommandType.PRINT_FIELD_ASCENDING_DESCRIPTION
        );
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
