package Commands;

import Models.CollectionManager;
import SharedModels.Response;
import SharedUtility.ResponseStatus;
import Utility.Command;
import SharedUtility.CommandType;
import SharedUtility.MusicGenre;

import java.util.ArrayList;

public class CountLessThanGenre extends Command {
    private final CollectionManager manager;
    public CountLessThanGenre(CollectionManager manager) {
        super("count_less_than_genre", CommandType.COUNT_LESS_THAN_GENRE);
        this.manager = manager;
    }


    @Override
    public Response execute(ArrayList<Object> args) {
        try {
            return manager.count_less_than_genre((MusicGenre) args.get(0));
        } catch (Exception e){
            return new Response(ResponseStatus.ERROR, e.getMessage());
        }
    }
}
