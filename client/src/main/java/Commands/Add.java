package Commands;

import SharedModels.MusicBand;
import SharedUtility.CommandType;
import Utility.Command;

import java.util.ArrayList;
import java.util.Scanner;

public class Add extends Command {
    private Scanner input;
    public Add(Scanner input) {
        super("add", CommandType.ADD);
        this.input = input;
    }

    @Override
    public ArrayList<Object> getArgs(String arguments) {
        var args = new ArrayList<>();
        MusicBand band = new MusicBand();
        band.input_data(input, false);
        args.add(band);
        return args;
    }

    @Override
    public ArrayList<Object> getArgs(String arguments, Scanner file_input) {
        var args = new ArrayList<>();
        MusicBand band = new MusicBand();
        band.input_data(file_input, true);
        args.add(band);
        return args;
    }
}
