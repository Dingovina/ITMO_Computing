package Models;

import SharedModels.MusicBand;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Scanner;

public class DumpManager {
    private String fileName;
    private Scanner input;

    public DumpManager(String fileName, Scanner input) {
        this.fileName = fileName;
        this.input = input;
    }

    public PriorityQueue<MusicBand> readCollection(){
        PriorityQueue<MusicBand> collection = new PriorityQueue<>();
        try {
            File file = new File(fileName);
            if (file.length() == 0){
                PrintStream printStream = new PrintStream(fileName);
                printStream.println("[]");
                printStream.close();
            }
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.findAndRegisterModules();

            String content = new String(Files.readAllBytes(Paths.get(file.toURI())));
            JSONArray jarray = new JSONArray(content);

            HashSet<Long> id_list = new HashSet<>();
            for (int i=0;i<jarray.length();i++){
                JSONObject jband = jarray.getJSONObject(i);
                MusicBand band = objectMapper.readValue(jband.toString(), MusicBand.class);
                if (band.valid() && !id_list.contains(band.getId())) {
                    collection.add(band);
                    id_list.add(band.getId());
                }
                else {
                    System.out.println("В файле содержится некорректный объект.");
                    System.exit(0);
                }
            }
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("Ошибка при чтении файла.");
            System.exit(0);
        }
        return collection;
    }

    public void writeCollection(PriorityQueue<MusicBand> collection) {
        try {
            PrintStream printStream = new PrintStream(fileName);
            JSONArray jsonCollection = new JSONArray();
            for (var band : collection){
                JSONObject jsonBand = new JSONObject(band);
                jsonCollection.put(jsonBand);
            }
            printStream.println(jsonCollection);
            System.out.println("Коллекция успешна сохранена в файл!");
        } catch (Exception exception) {
            System.out.println("Загрузочный файл не может быть открыт!");
            System.out.println("Введите название другого файла для переноса коллекции: ");
            String newFileName = input.nextLine().strip();
            writeCollection(collection, newFileName);
        }
    }

    public void writeCollection(PriorityQueue<MusicBand> collection, String savingFileName) {
        try {
            PrintStream printStream = new PrintStream(savingFileName);
            JSONArray jsonCollection = new JSONArray();
            for (var band : collection){
                JSONObject jsonBand = new JSONObject(band);
                jsonCollection.put(jsonBand);
            }

            fileName = savingFileName;
            printStream.println(jsonCollection);
            System.out.println("Коллекция успешна сохранена в файл!");
        } catch (Exception exception) {
            System.out.println("Загрузочный файл не может быть открыт!");
        }
    }
}
