package Commands;

import Models.CollectionManager;
import SharedModels.Response;
import SharedUtility.ResponseStatus;
import Utility.Command;
import SharedUtility.CommandType;

import java.util.ArrayList;

public class CountByDescription extends Command {
    private CollectionManager manager;
    public CountByDescription(CollectionManager manager) {
        super("count_by_description", CommandType.COUNT_BY_DESCRIPTION);
        this.manager = manager;
    }

    @Override
    public Response execute(ArrayList<Object> args) {
        try {
            return manager.count_by_description((String) args.get(0));
        } catch (Exception e){
            return new Response(ResponseStatus.ERROR, e.getMessage());
        }
    }
}
