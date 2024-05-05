package Commands;

import Models.CollectionManager;
import SharedModels.MusicBand;
import SharedModels.Response;
import SharedUtility.ResponseStatus;
import Utility.Command;
import SharedUtility.CommandType;

import java.util.ArrayList;

public class Add extends Command {
    CollectionManager manager;
    public Add(CollectionManager manager) {
        super("add", CommandType.ADD);
        this.manager = manager;
    }

    @Override
    public Response execute(ArrayList<Object> args) {
        try {
            return manager.add((MusicBand) args.get(0));
        } catch (Exception e){
            return new Response(ResponseStatus.ERROR, e.getMessage());
        }
    }
}
