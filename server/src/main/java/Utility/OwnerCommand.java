package Utility;

import SharedModels.Response;
import SharedUtility.CommandType;
import SharedUtility.ResponseStatus;

import java.util.ArrayList;

public abstract class OwnerCommand extends Command {
    public OwnerCommand(String name, CommandType commandType) {
        super(name, commandType);
    }

    @Override
    public Response execute(ArrayList<Object> args) {
        return new Response(ResponseStatus.ERROR, "Вы не можете изменять группы, созданные не вами.");
    }

    abstract public Response execute(ArrayList<Object> args, String user);
}
