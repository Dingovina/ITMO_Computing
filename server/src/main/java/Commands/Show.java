package Commands;

import Models.CollectionManager;
import SharedModels.Response;
import Utility.Command;
import SharedUtility.CommandType;

import java.util.ArrayList;

public class Show extends Command {

    private CollectionManager manager;
    public Show(CollectionManager manager) {
        super("show", CommandType.SHOW);
        this.manager = manager;
    }

    @Override
    public Response execute(ArrayList<Object> args) {
        return manager.show();
    }
}
