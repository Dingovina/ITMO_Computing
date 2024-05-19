import Models.Client;
import Models.CommandManager;
import SharedModels.Request;
import SharedModels.Response;
import SharedUtility.CommandType;
import SharedUtility.ResponseStatus;

import java.net.InetAddress;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        InetAddress host = null;
        Integer port = null;
        try {
            host = InetAddress.getByName(args[0]);
            port = Integer.parseInt(args[1]);
        } catch (Exception e) {
            System.out.println("Неверные аргументы.");
            System.out.println("Необходимо передать адрес и порт в качестве аргументов.");
            System.exit(0);
        }
        Client client = new Client(host, port);
        Scanner input = new Scanner(System.in);

        String username;
        String password;
        System.out.println("Необходима авторизация.");
        while (true){
            String user_input;
            do {
                System.out.println("Введите 'reg', чтобы создать новый аккаунт.");
                System.out.println("Введите 'log', чтобы войти в аккаунт.");
                user_input = input.nextLine();
            } while (!Objects.equals(user_input, "reg") && !Objects.equals(user_input, "log"));
            if (Objects.equals(user_input, "log")) {
                System.out.println("Введите логин:");
                username = input.nextLine();
                System.out.println("Введите пароль:");
                password = input.nextLine();
                Response resp = client.authorize(username, password);
                if (resp.getStatus() == ResponseStatus.OK) {
                    System.out.println(resp.getMessage());
                    break;
                } else {
                    System.out.println("Ошибка при авторизации.");
                    System.out.println(resp.getMessage());
                }
            }
            else{
                System.out.println("Введите имя для нового аккаунта:");
                username = input.nextLine();
                System.out.println("Введите пароль для нового аккаунта:");
                password = input.nextLine();

                Response r = client.register(username, password);
                if (r.getStatus() == ResponseStatus.OK){
                    System.out.println(r.getMessage());
                    break;
                }
                else {
                    System.out.println("Ошибка при регистрации.");
                    System.out.println(r.getMessage());
                }
            }
        }
        CommandManager manager = new CommandManager(input, username);

        System.out.println("Приложение запущено!");

        while (true) {
            String user_line = input.nextLine();
            Request request = manager.call(user_line, username);
            if (Objects.isNull(request) || !request.isValid()){
                System.out.println("Некорректная команда.");
                continue;
            }
            if (request.commandType() == CommandType.EXECUTE_SCRIPT){
                boolean ok_flag = true;
                ArrayList<Request> queue = manager.execute_script((String) request.arguments().get(0));
                if (Objects.isNull(queue)){
                    System.out.println("Ошибка при попытке выполнения скрипта.");
                    continue;
                }
                System.out.println("Начинаю выполнение команд из указанного файла.");
                for (Request cur_request : queue){
                    Response response = client.sendRequest(cur_request);
                    if (response.getStatus() == ResponseStatus.ERROR){
                        System.out.println("Некорректная команда в файле.");
                        System.out.println("Завершаю исполнения команд из файла.");
                        ok_flag = false;
                        break;
                    }
                }
                if (ok_flag){
                    System.out.println("Все команды из файла успешно выполнены.");
                }
                continue;
            }

            Response response = client.sendRequest(request);

            switch (response.getStatus()){
                case OK -> System.out.println("Запрос обработан.");
                case ERROR -> System.out.println("Ошибка!");
            }
            System.out.println(response.getMessage());
        }
    }
}
