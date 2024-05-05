package Commands;

import Models.CollectionManager;
import SharedModels.Response;
import Utility.Command;
import SharedUtility.CommandType;

import java.util.ArrayList;

public class RemoveById extends Command {
    private CollectionManager manager;
    public RemoveById(CollectionManager manager) {
        super("remove_by_id", CommandType.REMOVE_BY_ID);
        this.manager = manager;
    }

    @Override
    public Response execute(ArrayList<Object> args) {
        return manager.remove_by_id((Long) args.get(0));
    }
}
