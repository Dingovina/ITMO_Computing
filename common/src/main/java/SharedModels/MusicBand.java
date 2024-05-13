package SharedModels;

import SharedUtility.Inputable;
import SharedUtility.MusicGenre;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.InputMismatchException;
import java.util.Scanner;

public class MusicBand implements Comparable<MusicBand>, Inputable, Serializable {
    private Long id; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private ZonedDateTime creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private int numberOfParticipants; //Значение поля должно быть больше 0
    private Long albumsCount; //Поле не может быть null, Значение поля должно быть больше 0
    private String description; //Поле может быть null
    private MusicGenre genre; //Поле не может быть null
    private Person frontMan; //Поле может быть null

    public MusicBand(){
        id = -1L;
        creationDate = ZonedDateTime.now();
        coordinates = new Coordinates();
        frontMan = new Person();
    }

    public boolean load_data(Scanner scan){
        try{
            id = scan.nextLong();
            scan.nextLine();
            creationDate = ZonedDateTime.of(LocalDateTime.parse(scan.nextLine().split("\\.")[0], DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")), ZoneId.systemDefault());
        } catch (Exception e){
            System.out.println(e.getMessage());
            System.out.println("Ошибка при загрузке MusicBand.");
            return false;
        }
        input_data(scan, true);
        return true;
    }

    @Override
    public void input_data(Scanner scan, boolean is_reading){
        input_name(scan, is_reading);
        coordinates.input_data(scan, is_reading);
        input_numberOfParticipants(scan, is_reading);
        input_albumsCount(scan, is_reading);
        input_description(scan, is_reading);
        input_genre(scan, is_reading);
        frontMan.input_data(scan, is_reading);
    }

    private void input_name(Scanner input, boolean is_reading){
        if (!is_reading)
            System.out.print("Введите название группы: ");
        name = input.nextLine();
        while (name.isBlank() && !is_reading){
            System.out.println("Название группы не может быть пустым.");
            System.out.print("Введите название группы: ");
            name = input.nextLine();
        }
    }

    private void input_numberOfParticipants(Scanner input, boolean is_reading){
        if (!is_reading)
            System.out.print("Введите число участников группы: ");
        try {
            numberOfParticipants = input.nextInt();
        } catch (InputMismatchException e){
            input.nextLine();
        }
        while (numberOfParticipants < 1 && !is_reading) {
            System.out.println("Значение должно быть целым положительным числом.");
            System.out.print("Введите число участников группы: ");
            try {
                numberOfParticipants = input.nextInt();
            } catch (InputMismatchException e){
                input.nextLine();
            }
        }
        input.nextLine();
    }

    private void input_albumsCount(Scanner input, boolean is_reading){
        do {
            if (!is_reading)
                System.out.print("Введите количество альбомов группы: ");
            try {
                albumsCount = input.nextLong();
            } catch (InputMismatchException e){
                input.nextLine();
                albumsCount = -1L;
            }
            if (albumsCount <= 0 && !is_reading){
                System.out.println("Значение должно быть целым положительным числом.");
            }
        } while (albumsCount <= 0 && !is_reading);
        input.nextLine();
    }

    private void input_description(Scanner input, boolean is_reading){
        do {
            if (!is_reading)
                System.out.print("Введите описание группы: ");
            description = input.nextLine();
            if (description.isEmpty() && !is_reading){
                System.out.println("Описание не должно быть пустым.");
            }
        } while (description.isEmpty() && !is_reading);
    }

    private void input_genre(Scanner input, boolean is_reading){
        if (!is_reading) {
            System.out.println("Представленные жанры: ");
            for (int i = 0; i < MusicGenre.values().length; ++i) {
                System.out.println(i + 1 + " - " + MusicGenre.values()[i]);
            }
        }
        MusicGenre g = MusicGenre.MATH_ROCK;
        String i;
        do {
            if (!is_reading)
                System.out.println("Введите жанр группы: ");
            try{
                i = input.nextLine();
                g = MusicGenre.valueOf(i);
            } catch (Exception e){
                i = "";
            }
            if (i.isEmpty() && !is_reading) System.out.println("Необходимо ввести жанр");
        } while (i.isEmpty() && !is_reading);

        try {
            genre = g;
        } catch (IndexOutOfBoundsException ignored){}
    }

    @Override
    public int compareTo(MusicBand other_band) {
        return this.id.compareTo(other_band.id);
    }

    @Override
    public String toString() {
        return "{\n" +
                "\"id\": " + id + ",\n" +
                "\"name\": \"" + name + "\",\n" +
                "\"creationDate\": \"" + creationDate.format(DateTimeFormatter.ISO_DATE_TIME) + "\",\n" +
                "\"coordinates\": \"" + coordinates + "\",\n" +
                "\"numberOfParticipants\": " + numberOfParticipants + ",\n" +
                "\"albumsCount\": " + albumsCount + ",\n" +
                "\"description\": \"" + description + "\",\n" +
                "\"genre\": \"" + genre + "\",\n" +
                "\"frontMan\": " + frontMan + "\n" +
                "}";
    }

    public String getDescription(){
        return description;
    }

    public MusicGenre getGenre(){
        return genre;
    }

    public String getName() {
        return name;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public int getNumberOfParticipants() {
        return numberOfParticipants;
    }

    public Long getAlbumsCount() {
        return albumsCount;
    }

    public Person getFrontMan() {
        return frontMan;
    }

    public ZonedDateTime getCreationDate() {
        return creationDate;
    }
}

