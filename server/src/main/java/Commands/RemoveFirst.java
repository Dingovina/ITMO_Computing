package Commands;

import Models.CollectionManager;
import SharedModels.Response;
import Utility.Command;
import SharedUtility.CommandType;

import java.util.ArrayList;

public class RemoveFirst extends Command {
    private CollectionManager manager;
    public RemoveFirst(CollectionManager manager) {
        super("remove_first", CommandType.REMOVE_FIRST);
        this.manager = manager;
    }

    @Override
    public Response execute(ArrayList<Object> args) {
        return manager.remove_first();
    }
}
