package Models;

import SharedModels.MusicBand;
import SharedModels.Request;
import SharedModels.Response;
import SharedUtility.ResponseStatus;
import Utility.Command;
import SharedUtility.MusicGenre;
import Commands.*;
import Utility.OwnerCommand;

import java.time.format.DateTimeFormatter;
import java.util.*;

public class CollectionManager {
    private PriorityQueue<MusicBand> bands;
    private final ArrayList<String> last_commands = new ArrayList<>();
    private final java.time.ZonedDateTime creationDate;
    private final ArrayList<Command> publicCommands = new ArrayList<>();
    private final ArrayList<OwnerCommand> ownerCommands = new ArrayList<>();
    private final DumpManager dumpManager;

    public CollectionManager(DumpManager dumpManager){
        this.dumpManager = dumpManager;
        this.bands = dumpManager.readCollection();
        creationDate = java.time.ZonedDateTime.now();
        ownerCommands.add(new Register(dumpManager.getConnection()));
        ownerCommands.add(new Authorization(dumpManager.getConnection()));
        ownerCommands.add(new Add(dumpManager.getConnection()));
        ownerCommands.add(new Clear(dumpManager.getConnection()));
        ownerCommands.add(new RemoveById(dumpManager.getConnection()));
        ownerCommands.add(new RemoveFirst(dumpManager.getConnection()));
        ownerCommands.add(new RemoveLower(dumpManager.getConnection()));
        ownerCommands.add(new Update(dumpManager.getConnection()));
        publicCommands.add(new CountByDescription(this));
        publicCommands.add(new CountLessThanGenre(this));
        publicCommands.add(new Help(this));
        publicCommands.add(new History(this));
        publicCommands.add(new Info(this));
        publicCommands.add(new PrintFieldAscendingDescription(this));
        publicCommands.add(new Save(this));
        publicCommands.add(new Show(this));
    }

    public Response call(Request request){
        for (Command command : publicCommands){
            if (command.getCommandType().equals(request.commandType())){
                Response r = command.execute(request.arguments());
                last_commands.add(command.getName());
                save();
                return r;
            }
        }
        for (OwnerCommand command : ownerCommands){
            if (command.getCommandType().equals(request.commandType())){
                Response r = command.execute(request.arguments(), request.username());
                if (!Objects.equals(command.getName(), "authorization") && !Objects.equals(command.getName(), "register")) {
                    last_commands.add(command.getName());
                }
                save();
                return r;
            }
        }
        return new Response(ResponseStatus.ERROR, "Command not found.");
    }

    public Response help(){
        String message = "";
        message += """
                Список доступных команд:
                help : вывести справку по доступным командам\\n" +
                "info : вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)\\n" +
                "show : вывести в стандартный поток вывода все элементы коллекции в строковом представлении\\n" +
                "add {element} : добавить новый элемент в коллекцию\\n" +
                "update id {element} : обновить значение элемента коллекции, id которого равен заданному\\n" +
                "remove_by_id id : удалить элемент из коллекции по его id\\n" +
                "clear : очистить коллекцию\\n" +
                "execute_script file_name : считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме.\\n" +
                "exit : завершить программу (без сохранения в файл)\\n" +
                "remove_first : удалить первый элемент из коллекции\\n" +
                "remove_lower {element} : удалить из коллекции все элементы, меньшие, чем заданный\\n" +
                "history : вывести последние 13 команд (без их аргументов)\\n" +
                "count_by_description description : вывести количество элементов, значение поля description которых равно заданному\\n" +
                "count_less_than_genre genre : вывести количество элементов, значение поля genre которых меньше заданного\\n" +
                "print_field_ascending_description : вывести значения поля description всех элементов в порядке возрастания""";
        return new Response(ResponseStatus.OK, message);
    }

    public Response info(){
        String message = "";
        message += "Коллекция содержит объекты типа MusicBand.\n";
        message += "Объекты хранятся в виде java.util.PriorityQueue\n";
        message += "Дата создания коллекции " + creationDate.format(DateTimeFormatter.ISO_DATE_TIME) + "\n";
        message += "Количество объектов в коллекции " + bands.size() + "\n";

        return new Response(ResponseStatus.OK, message);
    }

    public Response show(){
        StringBuilder message = new StringBuilder();

        message.append("Содержимое коллекции: \n");
        for (MusicBand band : bands){
            message.append(band).append("\n");
        }

        return new Response(ResponseStatus.OK, message.toString());
    }


    public Response save(){
        bands = dumpManager.readCollection();
        return new Response(ResponseStatus.OK, "Collection saved.");
    }

    public Response history(){
        StringBuilder message = new StringBuilder();
        if (last_commands.size() < 13){
            message.append("Последние ").append(last_commands.size()).append(" команд:\n");
            for (String command : last_commands) message.append(command).append("\n");
        }
        else{
            message.append("Последние 13 команд: \n");
            for (int i = last_commands.size() - 13; i < last_commands.size(); ++i){
                message.append(last_commands.get(i)).append("\n");
            }
        }
        return new Response(ResponseStatus.OK, message.toString());
    }

    public Response count_by_description(String description){
        String message = "";

        int result = 0;
        for (MusicBand band : bands){
            if (band.getDescription().equals(description)){
                result++;
            }
        }

        message += "В коллекции найдено " + result + " объектов с таким описанием.\n";
        return new Response(ResponseStatus.OK, message);
    }

    public Response count_less_than_genre(MusicGenre genre){
        String message = "";

        int result = 0;
        for (MusicBand band : bands){
            if (band.getGenre() == genre) result++;
        }

        message += "В коллекции найдено " + result + " объектов с таким жанром.\n";
        return new Response(ResponseStatus.OK, message);
    }

    public Response print_field_ascending_description(){
        StringBuilder message = new StringBuilder();

        ArrayList<String> all_descriptions = new ArrayList<>();
        for (MusicBand band : bands){
            all_descriptions.add(band.getDescription());
        }

        Collections.sort(all_descriptions);
        message.append("Описания всех объектов в порядке возрастания: \n");
        for (String description : all_descriptions){
            message.append(description).append("\n");
        }

        return new Response(ResponseStatus.OK, message.toString());
    }
}
