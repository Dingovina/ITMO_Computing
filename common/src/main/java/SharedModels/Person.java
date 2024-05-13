package SharedModels;

import SharedUtility.Color;
import SharedUtility.Country;
import SharedUtility.Inputable;

import java.io.Serializable;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Person implements Inputable, Serializable {
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Float weight; //Поле может быть null, Значение поля должно быть больше 0
    private Color eyeColor; //Поле не может быть null
    private Color hairColor; //Поле не может быть null
    private Country nationality; //Поле может быть null
    private Location location; //Поле может быть null

    public Person(){
        location = new Location();
    }

    @Override
    public void input_data(Scanner scan, boolean is_reading) {
        input_name(scan, is_reading);
        input_weight(scan, is_reading);
        input_eyeColor(scan, is_reading);
        input_hairColor(scan, is_reading);
        input_nationality(scan, is_reading);
        location.input_data(scan, is_reading);
    }

    public void input_name(Scanner input, boolean is_reading){
        if (!is_reading)
            System.out.print("Введите имя солиста: ");
        name = input.nextLine();
        while (name.isBlank() && !is_reading) {
            System.out.println("Имя солиста не может быть пустым.");
            System.out.print("Введите имя солиста: ");
            name = input.nextLine();
        }
    }

    public void input_weight(Scanner input, boolean is_reading){
        do {
            if (!is_reading)
                System.out.print("Введите вес солиста: ");
            try{
                weight = input.nextFloat();
            } catch (InputMismatchException e){
                input.nextLine();
                weight = -1f;
            }
            if (weight <= 0 && !is_reading) {
                System.out.println("Вес должен быть положительным числом.");
            }
        } while (weight <= 0 && !is_reading);
        input.nextLine();
    }

    public void input_eyeColor(Scanner input, boolean is_reading){
        if (!is_reading) {
            System.out.println("Представленные цвета: ");
            for (int i = 0; i < Color.values().length; ++i) {
                System.out.println(i + 1 + " - " + Color.values()[i]);
            }
        }
        String i;
        Color c = Color.BLACK;
        do {
            if (!is_reading)
                System.out.println("Введите цвет глаз солиста: ");
            try{
                i = input.nextLine();
                c = Color.valueOf(i);
            } catch (Exception e){
                i = "";
            }
            if (i.isEmpty() && !is_reading) System.out.println("Необходимо ввести цвет");
        } while (i.isEmpty() && !is_reading);

        try {
            eyeColor = c;
        } catch (IndexOutOfBoundsException ignored){}
    }

    public void input_hairColor(Scanner input, boolean is_reading){
        if (!is_reading) {
            System.out.println("Представленные цвета: ");
            for (int i = 0; i < Color.values().length; ++i) {
                System.out.println(i + 1 + " - " + Color.values()[i]);
            }
        }
        String i;
        Color c = Color.BLACK;
        do {
            if (!is_reading)
                System.out.println("Введите цвет волос солиста: ");
            try{
                i = input.nextLine();
                c = Color.valueOf(i);
            } catch (Exception e){
                i = "";
            }
            if (i.isEmpty() && !is_reading) System.out.println("Необходимо ввести цвет");
        } while (i.isEmpty() && !is_reading);

        try {
            hairColor = c;
        } catch (IndexOutOfBoundsException ignored){}
    }

    public void input_nationality(Scanner input, boolean is_reading){
        if (!is_reading) {
            System.out.println("Представленные страны: ");
            for (int i = 0; i < Country.values().length; ++i) {
                System.out.println(i + 1 + " - " + Country.values()[i]);
            }
        }
        String i;
        Country c = Country.USA;
        do {
            if (!is_reading)
                System.out.println("Введите страну солиста: ");
            try{
                i = input.nextLine();
                c = Country.valueOf(i);
            } catch (Exception e){
                i = "";
            }
            if (i.isEmpty() && !is_reading) System.out.println("Необходимо ввести страну");
        } while (i.isEmpty() && !is_reading);

        try {
            nationality = c;
        }catch (IndexOutOfBoundsException ignored){}
    }

    @Override
    public String toString() {
        return "{\n" +
                "\"name\": \"" + name + "\",\n" +
                "\"weight\": " + weight + ",\n" +
                "\"eyeColor\": \"" + eyeColor + "\",\n" +
                "\"hairColor\": \"" + hairColor + "\",\n" +
                "\"nationality\": \"" + nationality + "\",\n" +
                "\"location\": " + location + "\n" +
                "}";
    }

    public String getName() {
        return name;
    }

    public Color getEyeColor() {
        return eyeColor;
    }

    public Color getHairColor() {
        return hairColor;
    }

    public Country getNationality() {
        return nationality;
    }

    public Float getWeight() {
        return weight;
    }

    public Location getLocation() {
        return location;
    }
}
