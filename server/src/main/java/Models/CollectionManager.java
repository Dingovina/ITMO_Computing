package Models;

import SharedModels.MusicBand;
import SharedModels.Request;
import SharedModels.Response;
import SharedUtility.ResponseStatus;
import Utility.Command;
import SharedUtility.MusicGenre;
import Commands.*;

import java.sql.Connection;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class CollectionManager {
    private PriorityQueue<MusicBand> bands = new PriorityQueue<>();
    private ArrayList<String> last_commands = new ArrayList<>();
    private java.time.ZonedDateTime creationDate;
    private ArrayList<Command> commands_list = new ArrayList<>();
    private DumpManager dumpManager;

    public CollectionManager(DumpManager dumpManager){
        this.dumpManager = dumpManager;
        this.bands = dumpManager.readCollection();
        creationDate = java.time.ZonedDateTime.now();
        commands_list.add(new Add(dumpManager.getConnection()));
        commands_list.add(new Clear(dumpManager.getConnection()));
        commands_list.add(new CountByDescription(this));
        commands_list.add(new Help(this));
        commands_list.add(new History(this));
        commands_list.add(new Info(this));
        commands_list.add(new PrintFieldAscendingDescription(this));
        commands_list.add(new RemoveById(dumpManager.getConnection()));
        commands_list.add(new RemoveFirst(dumpManager.getConnection()));
        commands_list.add(new RemoveLower(dumpManager.getConnection()));
        commands_list.add(new Save(this));
        commands_list.add(new Show(this));
        commands_list.add(new Update(dumpManager.getConnection()));
    }

    public Response call(Request request){
        for (Command command : commands_list){
            if (command.getCommandType().equals(request.getCommandType())){
                Response r = command.execute(request.getArguments());
                save();

                return r;
            }
        }
        return new Response(ResponseStatus.ERROR, "Command not found.");
    }

    public Response help(){
        last_commands.add("help");
        String message = "";
        message += "Список доступных команд:\n" +
                "help : вывести справку по доступным командам\\n\" +\n" +
                "\"info : вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)\\n\" +\n" +
                "\"show : вывести в стандартный поток вывода все элементы коллекции в строковом представлении\\n\" +\n" +
                "\"add {element} : добавить новый элемент в коллекцию\\n\" +\n" +
                "\"update id {element} : обновить значение элемента коллекции, id которого равен заданному\\n\" +\n" +
                "\"remove_by_id id : удалить элемент из коллекции по его id\\n\" +\n" +
                "\"clear : очистить коллекцию\\n\" +\n" +
                "\"execute_script file_name : считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме.\\n\" +\n" +
                "\"exit : завершить программу (без сохранения в файл)\\n\" +\n" +
                "\"remove_first : удалить первый элемент из коллекции\\n\" +\n" +
                "\"remove_lower {element} : удалить из коллекции все элементы, меньшие, чем заданный\\n\" +\n" +
                "\"history : вывести последние 13 команд (без их аргументов)\\n\" +\n" +
                "\"count_by_description description : вывести количество элементов, значение поля description которых равно заданному\\n\" +\n" +
                "\"count_less_than_genre genre : вывести количество элементов, значение поля genre которых меньше заданного\\n\" +\n" +
                "\"print_field_ascending_description : вывести значения поля description всех элементов в порядке возрастания";
        return new Response(ResponseStatus.OK, message);
    }

    public Response info(){
        last_commands.add("info");

        String message = "";
        message += "Коллекция содержит объекты типа MusicBand.\n";
        message += "Объекты хранятся в виде java.util.PriorityQueue\n";
        message += "Дата создания коллекции " + creationDate.format(DateTimeFormatter.ISO_DATE_TIME) + "\n";
        message += "Количество объектов в коллекции " + bands.size() + "\n";

        return new Response(ResponseStatus.OK, message);
    }

    public Response show(){
        last_commands.add("show");
        String message = "";

        message += "Содержимое коллекции: \n";
        for (MusicBand band : bands){
            message += band + "\n";
        }

        return new Response(ResponseStatus.OK, message);
    }

    public Response update(long id, MusicBand newBand){
        last_commands.add("update");
        String message = "";

        boolean found = false;
        for (MusicBand band : bands){
            if (band.getId() == id){
                band.update(newBand);
                message += "Данные о группе изменены.\n";
                found = true;
                break;
            }
        }
        if (!found){
            message += "Группа с таким id не найдена.\n";
            return new Response(ResponseStatus.ERROR, message);
        }
        return new Response(ResponseStatus.OK, message);
    }

    public Response remove_by_id(long id){
        last_commands.add("remove_by_id");
        String message = "";

        boolean found = false;
        for (MusicBand band : bands){
            if (band.getId() == id){
                bands.remove(band);
                message += "Группа удалена из коллекции\n";
                found = true;
                break;
            }
        }
        if (!found){
            message += "Группа с таким id не найдена.\n";
            return new Response(ResponseStatus.ERROR, message);
        }
        return new Response(ResponseStatus.OK, message);
    }

    public Response clear(){
        last_commands.add("clear");
        String message = "";

        bands.clear();
        message += "Коллекция была отчищена\n";
        return new Response(ResponseStatus.OK, message);
    }

    public Response save(){
        bands = dumpManager.readCollection();
        return new Response(ResponseStatus.OK, "Collection saved.");
    }
    public Response remove_first(){
        last_commands.add("remove_first");
        String message = "";

        if (bands.isEmpty()){
            message += "Коллекция уже пуста.\n";
            return new Response(ResponseStatus.ERROR, message);
        }
        else{
            bands.poll();
            message += "Первый объект коллекции был удалён.";
            return new Response(ResponseStatus.OK, message);
        }
    }

    public Response remove_lower(MusicBand user_band){
        last_commands.add("remove_lower");
        bands.removeIf(band -> band.compareTo(user_band) < 0);
        String message = "";

        message += "Объекты меньше введенного были удалены.\n";
        return new Response(ResponseStatus.OK, message);
    }

    public Response history(){
        String message = "";
        if (last_commands.size() < 13){
            message += "Последние " + last_commands.size() + " команд:\n";
            for (String command : last_commands) message += command + "\n";
        }
        else{
            message += "Последние 13 команд: \n";
            for (int i = last_commands.size() - 13; i < last_commands.size(); ++i){
                message += last_commands.get(i) + "\n";
            }
        }
        last_commands.add("history");
        return new Response(ResponseStatus.OK, message);
    }

    public Response count_by_description(String description){
        last_commands.add("count_by_description");
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
        last_commands.add("count_less_than_genre");
        String message = "";

        int result = 0;
        for (MusicBand band : bands){
            if (band.getGenre() == genre) result++;
        }

        message += "В коллекции найдено " + result + " объектов с таким жанром.\n";
        return new Response(ResponseStatus.OK, message);
    }

    public Response print_field_ascending_description(){
        last_commands.add("print_field_ascending_description");
        String message = "";

        ArrayList<String> all_descriptions = new ArrayList<String>();
        for (MusicBand band : bands){
            all_descriptions.add(band.getDescription());
        }

        Collections.sort(all_descriptions);
        message += "Описания всех объектов в порядке возрастания: \n";
        for (String description : all_descriptions){
            message += description + "\n";
        }

        return new Response(ResponseStatus.OK, message);
    }
}
