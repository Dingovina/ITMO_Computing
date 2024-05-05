package Commands;

import Models.CollectionManager;
import SharedModels.Response;
import Utility.Command;
import SharedUtility.CommandType;

import java.util.ArrayList;

public class PrintFieldAscendingDescription extends Command {
    private CollectionManager manager;
    public PrintFieldAscendingDescription(CollectionManager manager) {
        super("print_field_ascending_description", CommandType.PRINT_FIELD_ASCENDING_DESCRIPTION);
        this.manager = manager;
    }

    @Override
    public Response execute(ArrayList<Object> args) {
        return manager.print_field_ascending_description();
    }
}
