package Commands;

import Models.CollectionManager;
import SharedModels.MusicBand;
import SharedModels.Response;
import SharedUtility.ResponseStatus;
import Utility.Command;
import SharedUtility.CommandType;

import java.util.ArrayList;

public class Update extends Command {
    private CollectionManager manager;
    public Update(CollectionManager manager) {
        super("update", CommandType.UPDATE);
        this.manager = manager;
    }

    @Override
    public Response execute(ArrayList<Object> args) {
        try {
            return manager.update((Long) args.get(0), (MusicBand) args.get(1));
        } catch (NumberFormatException e){
            return new Response(ResponseStatus.ERROR, "Wrong id.");
        } catch (Exception e){
            return new Response(ResponseStatus.ERROR, e.getMessage());
        }
    }
}
