package Commands;

import SharedModels.MusicBand;
import SharedUtility.CommandType;
import Utility.Command;

import java.util.ArrayList;
import java.util.Scanner;

public class Update extends Command {
    private Scanner input;
    public Update(Scanner input) {
        super("update", CommandType.UPDATE);
        this.input = input;
    }

    @Override
    public ArrayList<Object> getArgs(String arguments) {
        ArrayList<Object> args = new ArrayList<>();
        args.add(Long.parseLong(arguments));
        MusicBand band = new MusicBand();
        band.input_data(input, false);
        args.add(band);


        return args;
    }

    public ArrayList<Object> getArgs(String arguments, Scanner file_input) {
        ArrayList<Object> args = new ArrayList<>();
        args.add(Long.parseLong(arguments));
        MusicBand band = new MusicBand();
        band.input_data(file_input, true);
        args.add(band);


        return args;
    }
}
