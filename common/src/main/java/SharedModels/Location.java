package SharedModels;

import SharedUtility.Inputable;

import java.io.Serializable;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Location implements Inputable, Serializable {
    private long x;
    private Integer y; //Поле не может быть null
    private String name; //Поле не может быть null

    public boolean valid(){
        if (y == null) return false;
        if (name == null) return false;
        return true;
    }

    @Override
    public void input_data(Scanner scan, boolean is_reading) {
        input_x(scan, is_reading);
        input_y(scan, is_reading);
        input_name(scan, is_reading);
    }

    public void input_x(Scanner input, boolean is_reading){
        boolean none_flag = true;
        do{
            if (!is_reading)
                System.out.print("Введите координату X локации: ");
            try{
                x = input.nextLong();
                none_flag = false;
            } catch (InputMismatchException e){
                if (!is_reading)
                    System.out.println("Координата X должна быть целым числом.");
                input.nextLine();
            }
        }while (none_flag && !is_reading);

        input.nextLine();
    }

    public void input_y(Scanner input, boolean is_reading){
        boolean none_flag = true;
        do{
            if (!is_reading)
                System.out.print("Введите координату Y локации: ");
            try{
                y = input.nextInt();
                none_flag = false;
            } catch (InputMismatchException e){
                if (!is_reading)
                    System.out.println("Координата Y должна быть целым числом.");
                input.nextLine();
            }
        }while (none_flag && !is_reading);

        input.nextLine();
    }

    public void input_name(Scanner input, boolean is_reading){
        if (!is_reading)
            System.out.print("Введите название локации: ");
        name = input.nextLine();
        while (name.isEmpty() && !is_reading) {
            System.out.println("Название локации не может быть пустым.");
            System.out.print("Введите название локации: ");
            name = input.nextLine();
        }
    }

    @Override
    public String toString() {
        return "{\n" +
                "\"x\": " + x + ",\n" +
                "\"y\": " + y + ",\n" +
                "\"name\": \"" + name + "\"\n" +
                "}";
    }

    public String getName() {
        return name;
    }

    public Integer getY() {
        return y;
    }

    public long getX() {
        return x;
    }
}
