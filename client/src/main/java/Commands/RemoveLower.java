package Commands;

import SharedModels.MusicBand;
import SharedUtility.CommandType;
import Utility.Command;

import java.util.ArrayList;
import java.util.Scanner;


public class RemoveLower extends Command {
    private Scanner input;
    public RemoveLower(Scanner input) {
        super("remove_lower", CommandType.REMOVE_LOWER);
        this.input = input;
    }

    @Override
    public ArrayList<Object> getArgs(String arguments) {
        ArrayList<Object> args = new ArrayList<>();
        MusicBand band = new MusicBand();
        band.input_data(input, false);
        args.add(band);

        return args;
    }

    public ArrayList<Object> getArgs(String arguments, Scanner file_input) {
        ArrayList<Object> args = new ArrayList<>();
        MusicBand band = new MusicBand();
        band.input_data(file_input, true);
        args.add(band);

        return args;
    }
}
