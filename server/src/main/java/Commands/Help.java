package Commands;

import Models.CollectionManager;
import SharedModels.Response;
import Utility.Command;
import SharedUtility.CommandType;

import java.util.ArrayList;

public class Help extends Command {

    private CollectionManager manager;
    public Help(CollectionManager manager) {
        super("help", CommandType.HELP);
        this.manager = manager;
    }
    @Override
    public Response execute(ArrayList<Object> args) {
        return manager.help();
    }
}
