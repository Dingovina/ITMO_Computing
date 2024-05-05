package SharedModels;

import SharedUtility.Inputable;
import SharedUtility.MusicGenre;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.InputMismatchException;
import java.util.Scanner;

public class MusicBand implements Comparable<MusicBand>, Inputable, Serializable {
    private static HashSet<Long> id_list = new HashSet<>();
    private Long id; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private ZonedDateTime creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private int numberOfParticipants; //Значение поля должно быть больше 0
    private Long albumsCount; //Поле не может быть null, Значение поля должно быть больше 0
    private String description; //Поле может быть null
    private MusicGenre genre; //Поле не может быть null
    private Person frontMan; //Поле может быть null
    private long find_mex(){
        long mex = 0;
        for (long el : id_list){
            if (mex == el) mex++;
            else return mex;
        }
        return mex;
    }

    public MusicBand(){
        id = find_mex();
        id_list.add(id);
        creationDate = ZonedDateTime.now();
        coordinates = new Coordinates();
        frontMan = new Person();
    }

    public boolean valid(){
        if (name == null || name.isEmpty()) return false;
        if (!coordinates.valid()) return false;
        if (numberOfParticipants < 1) return false;
        if (albumsCount == null || albumsCount < 1) return false;
        if (description == null || description.isEmpty()) return false;
        if (genre == null) return false;
        if (!frontMan.valid()) return false;
        return true;
    }

    public void read_data(Scanner scan){
        input_data(scan, true);
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
        int i = 0;
        do {
            if (!is_reading)
                System.out.println("Введите число, соответствующее жанру группы: ");
            try{
                i = input.nextInt();
            } catch (InputMismatchException e){
                input.nextLine();
                i = 0;
            }
            if (i < 1 || i > MusicGenre.values().length && !is_reading) System.out.println("Необходимо ввести целое число от 1 до " + MusicGenre.values().length);
        } while (i < 1 || i > MusicGenre.values().length && !is_reading);
        input.nextLine();

        try {
            genre = MusicGenre.values()[i - 1];
        } catch (IndexOutOfBoundsException ignored){}
    }

    public void update(MusicBand band){
        System.out.println("Начинаем обновление группы.");
        this.name = band.name;
        this.genre = band.genre;
        this.frontMan = band.frontMan;
        this.albumsCount = band.albumsCount;
        this.numberOfParticipants = band.numberOfParticipants;
        this.coordinates = band.coordinates;
        this.description = band.description;
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

    public long getId() {
        return id;
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

