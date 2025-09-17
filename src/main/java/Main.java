package main.java;

import core.Model;
import dto.EstimationResult;

import java.util.ArrayList;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        runTestCases();

        try {

            Scanner scanner = new Scanner(System.in);
            var intervalsList = new ArrayList<Double>();

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (line.isEmpty()) {
                    continue;
                }
                if (line.equals("^D") || !scanner.hasNextLine()) {
                    break;
                }
                try {
                    var value = Integer.parseInt(line);
                    if (value < 0 || value > 2000000000) {
                        System.err.println("Предупреждение: число " + value + " вне диапазона 0-2000000000, будет проигнорировано");
                        break;
                    }
                    intervalsList.add((double) value);;
                } catch (NumberFormatException e) {
                    System.err.println("Ошибка: неверный формат числа - '" + line + "'");
                    break;
                }
            }
            scanner.close();


            double[] bugIntervals = new double[intervalsList.size()];
            for (int i = 0; i < intervalsList.size(); i++) {
                bugIntervals[i] =  intervalsList.get(i);
            }


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

private static void runTestCases() {
    System.out.println("=== ТЕСТОВЫЕ ПРОВЕРКИ ===");

    // Тест 1: Пустой массив
    testCase(new double[]{}, "Пустой массив");

    // Тест 2: Один элемент
    testCase(new double[]{10.0}, "Один элемент");

    // Тест 3: Несколько элементов
    testCase(new double[]{5.0, 15.0, 25.0}, "Несколько элементов");

    // Тест 4: Большие значения
    testCase(new double[]{100.0, 200.0}, "Большие значения");

    System.out.println("=== КОНЕЦ ТЕСТОВ ===");
    System.out.println();
}

private static void testCase(double[] testData, String testName) {
    try {
        var model = new Model();
        EstimationResult result = model.estimate(testData);

        System.out.println("Тест: " + testName);
        System.out.println("  Данные: " + java.util.Arrays.toString(testData));
        System.out.println("  Результат: totalBugs=" + result.getTotalBugs() +
                ", nextBugTime=" + result.getNextBugTime() +
                ", totalTestingTime=" + result.getTotalTestingTime());
        System.out.println();

    } catch (Exception e) {
        System.err.println("Тест '" + testName + "' упал с ошибкой: " + e.getMessage());
    }
}
}

