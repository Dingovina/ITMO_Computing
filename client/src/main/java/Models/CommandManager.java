package Models;

import Commands.*;
import SharedModels.Request;
import SharedModels.Response;
import SharedUtility.CommandType;
import Utility.*;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class CommandManager {
    private ArrayList<Command> commands_list = new ArrayList<>();
    private HashMap<String, Boolean> execution_trace = new HashMap<>();
    private boolean file_mode = false;
    private Scanner file_input;

    public CommandManager(Scanner input) {
        commands_list.add(new Add(input));
        commands_list.add(new Clear());
        commands_list.add(new CountByDescription());
        commands_list.add(new CountLessThanGenre());
        commands_list.add(new ExecuteScript());
        commands_list.add(new Exit());
        commands_list.add(new Help());
        commands_list.add(new History());
        commands_list.add(new Info());
        commands_list.add(new PrintFieldAscendingDescription());
        commands_list.add(new RemoveById());
        commands_list.add(new RemoveFirst());
        commands_list.add(new RemoveLower(input));
        commands_list.add(new Show());
        commands_list.add(new Update(input));
    }

    public Request call(String user_line) {
        user_line += " ";
        String command_name = user_line.split(" ", 2)[0];
        String arguments = user_line.split(" ", 2)[1].trim();
        for (Command command : commands_list) {
            if (command.getName().equals(command_name)) {
                try {
                    ArrayList<Object> args;
                    if (file_mode){
                        args = command.getArgs(arguments, file_input);
                    }
                    else {
                        args = command.getArgs(arguments);
                    }
                    Request request;
                    request = new Request(command.getCommandType(), args);
                    if (request.isExit()) exit();
                    else return request;
                } catch (Exception e) {
                    return null;
                }
            }
        }
        return null;
    }

    public ArrayList<Request> execute_script(String file_name) {
        ArrayList<Request> all_requests = new ArrayList<>();
        try {
            if (execution_trace.containsKey(file_name) && execution_trace.get(file_name)) {
//                System.out.println("В процессе выполнения команд из файла была обнаружена рекурсия.");
//                System.out.println("Выполнение команд из файла остановлено.");
                return null;
            }
            FileReader fr = new FileReader(file_name);
//            System.out.println("Начинаю выполнение команд из указанного файла.");
            Scanner file_input = new Scanner(fr);
            this.file_input = file_input;
            file_mode = true;
            execution_trace.put(file_name, true);
            while (file_input.hasNextLine()) {
                String user_line = file_input.nextLine();
                try {
                    Request request = call(user_line);
                    if (request.getCommandType() == CommandType.EXECUTE_SCRIPT){
                        ArrayList<Request> new_queue = execute_script((String) request.getArguments().get(0));
                        all_requests.addAll(new_queue);
                        this.file_input = file_input;
                    }
                    else {
                        all_requests.add(request);
                    }
                } catch (Exception e) {
//                    System.out.println("Ошибка во время выполнения команды из файла.");
//                    System.out.println("Выполнение команд из файла прекращено.");
                    return null;
                }
            }

            fr.close();
            file_mode = false;
            execution_trace.put(file_name, false);
            return all_requests;
        } catch (FileNotFoundException e) {
//            System.out.println("Файл не найден.");
            return null;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void exit() {
        System.out.println("Завершение работы.");
        System.exit(0);
    }
}