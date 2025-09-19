package main.java;
import main.java.core.Model;
import main.java.dto.EstimationResult;
import java.util.ArrayList;
import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
        try {
            var scanner = new Scanner(System.in);
            var intervalsList = new ArrayList<Double>();
            while (scanner.hasNextLine()) {
                var line = scanner.nextLine().trim();
                if (line.isEmpty()) {
                    continue;
                }
                if (line.equals("^D")) {
                    break;
                }
                try {
                    var value = Integer.parseInt(line);
                    if (value < 0 || value > 2000000000) {
                        throw new IllegalArgumentException("Предупреждение: число " + value + " вне диапазона 0-2000000000");
                    }
                    intervalsList.add((double) value);
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException("Ошибка: неверный формат числа - '" + line + "'", e);
                }
            }
            scanner.close();
            var bugIntervals = intervalsList.stream().mapToDouble(Double::doubleValue).toArray();
            var model = new Model();
            EstimationResult result = model.estimate(bugIntervals);
            System.out.println("Общее число ошибок в программной системе: " + result.getTotalBugs());
            System.out.println("Время до появления следующей ошибки: " + result.getNextBugTime());
            System.out.println("Время до окончания тестирования: " + result.getTotalTestingTime());
     }  catch (Throwable e) {
        var err = e;
        do {
            System.err.println("Произошла ошибка: " + err.getMessage());
            err.printStackTrace();
            err = err.getCause();
        } while (err != null);
    }
    }
}
