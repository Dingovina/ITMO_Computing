package Commands;

import SharedUtility.CommandType;
import SharedUtility.MusicGenre;
import Utility.Command;

import java.util.ArrayList;
import java.util.Scanner;

public class CountLessThanGenre extends Command {
    public CountLessThanGenre() {

        super("count_less_than_genre", CommandType.COUNT_LESS_THAN_GENRE);
    }

    @Override
    public ArrayList<Object> getArgs(String arguments) {
        ArrayList<Object> args = new ArrayList<>();
        try {
            args.add(MusicGenre.valueOf(arguments));
        } catch (Exception e){
            args.add(null);
        }

        return args;
    }

    @Override
    public ArrayList<Object> getArgs(String arguments, Scanner file_input) {
        return getArgs(arguments);
    }
}
