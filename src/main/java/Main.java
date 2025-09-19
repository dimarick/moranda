package main.java;
import main.java.core.Model;
import main.java.dto.EstimationResult;

import java.security.InvalidParameterException;
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
                        throw new InvalidParameterException("Предупреждение: число " + value + " вне диапазона 0-2000000000");
                    }
                    intervalsList.add((double) value);;
                } catch (NumberFormatException e) {
                    throw new InvalidParameterException("Ошибка: неверный формат числа - '" + line + "'");
                }
            }
            scanner.close();
            var bugIntervals = intervalsList.stream().mapToDouble(Double::doubleValue).toArray();
            var model = new Model();
            EstimationResult result = model.estimate(bugIntervals);
            System.out.println("Общее число ошибок в программной системе: " + result.getTotalBugs());
            System.out.println("Время до появления следующей ошибки: " + result.getNextBugTime());
            System.out.println("Время до окончания тестирования: " + result.getTotalTestingTime());
        } catch (Exception e) {
            System.err.println("Произошла ошибка: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
