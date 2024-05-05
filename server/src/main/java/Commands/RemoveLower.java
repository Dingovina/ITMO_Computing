package Commands;

import Models.CollectionManager;
import SharedModels.MusicBand;
import SharedModels.Response;
import Utility.Command;
import SharedUtility.CommandType;

import java.util.ArrayList;

public class RemoveLower extends Command {
    private CollectionManager manager;
    public RemoveLower(CollectionManager manager) {
        super("remove_lower", CommandType.REMOVE_LOWER);
        this.manager = manager;
    }

    @Override
    public Response execute(ArrayList<Object> args) {
        return manager.remove_lower((MusicBand) args.get(0));
    }
}
