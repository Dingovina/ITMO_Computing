package SharedModels;

import SharedUtility.Inputable;

import java.io.Serializable;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Coordinates implements Inputable, Serializable {
    private Double x; //Максимальное значение поля: 985, Поле не может быть null
    private Double y; //Поле не может быть null

    public boolean valid(){
        if (x == null || x > 985) return false;
        if (y == null) return false;
        return true;
    }

    @Override
    public void input_data(Scanner scan, boolean is_reading) {
        if (!is_reading)
            System.out.println("Введите координаты.");
        do {
            if (!is_reading)
                System.out.print("Координата X: ");
            try {
                x = scan.nextDouble();
            } catch (InputMismatchException e){
                if (!is_reading)
                    System.out.println("Значение должно быть числом.");
                scan.nextLine();
                x = Double.NaN;
            }
            if (x > 985 && !is_reading){
                System.out.println("Значение не должно превышать 985.");
            }
        } while (x.isNaN() || x > 985 && !is_reading);

        do {
            if (!is_reading)
                System.out.print("Координата Y: ");
            try {
                y = scan.nextDouble();
            } catch (InputMismatchException e){
                if (!is_reading)
                    System.out.println("Значение должно быть числом.");
                scan.nextLine();
                y = Double.NaN;
            }
        } while (y.isNaN() && !is_reading);
        scan.nextLine();
    }

    @Override
    public String toString(){
        return "(" + x + "; " + y + ")";
    }

    public Double getX() {
        return x;
    }

    public Double getY() {
        return y;
    }
}
