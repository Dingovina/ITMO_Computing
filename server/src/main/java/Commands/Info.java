package Commands;

import Models.CollectionManager;
import SharedModels.Response;
import Utility.Command;
import SharedUtility.CommandType;

import java.util.ArrayList;

public class Info extends Command {

        private CollectionManager manager;
        public Info(CollectionManager manager) {
            super("info", CommandType.INFO);
            this.manager = manager;
        }

    @Override
    public Response execute(ArrayList<Object> args) {
        return manager.info();
    }
}
