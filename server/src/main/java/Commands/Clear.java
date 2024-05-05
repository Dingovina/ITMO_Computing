package Commands;

import Models.CollectionManager;
import SharedModels.Response;
import Utility.Command;
import SharedUtility.CommandType;

import java.util.ArrayList;

public class Clear extends Command {

    private CollectionManager manager;
    public Clear(CollectionManager manager) {
        super("clear", CommandType.CLEAR);
        this.manager = manager;
    }

    @Override
    public Response execute(ArrayList<Object> args) {
        return manager.clear();
    }
}
