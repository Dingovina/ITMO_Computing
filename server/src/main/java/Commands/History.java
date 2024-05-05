package Commands;

import Models.CollectionManager;
import SharedModels.Response;
import Utility.Command;
import SharedUtility.CommandType;

import java.util.ArrayList;

public class History extends Command {
    private CollectionManager manager;
    public History(CollectionManager manager) {
        super("history", CommandType.HISTORY);
        this.manager = manager;
    }


    @Override
    public Response execute(ArrayList<Object> args) {
        return manager.history();
    }
}
