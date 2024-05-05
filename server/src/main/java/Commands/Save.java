package Commands;

import Models.CollectionManager;
import SharedModels.Response;
import Utility.Command;
import SharedUtility.CommandType;

import java.util.ArrayList;

public class Save extends Command {
    private CollectionManager manager;
    public Save(CollectionManager manager) {
        super("save", CommandType.SAVE);
        this.manager = manager;
    }

    @Override
    public Response execute(ArrayList<Object> args) {
        return manager.save();
    }
}
