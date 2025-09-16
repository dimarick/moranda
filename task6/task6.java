package tesk6;

import core.Model;
import dto.EstimationResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class task6 {
    public static void task6(String[] args) {

        try {

            Scanner scanner = new Scanner(System.in);
            List<Integer> intervalsList = new ArrayList<>();

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (line.isEmpty()) {
                    continue;
                }

                try {
                    int value = Integer.parseInt(line);
                    if (value < 0 || value > 2000000000) {
                        System.err.println("Предупреждение: число " + value + " вне диапазона 0-2000000000, будет проигнорировано");
                        continue;
                    }
                    intervalsList.add(value);
                } catch (NumberFormatException e) {
                    System.err.println("Ошибка: неверный формат числа - '" + line + "'");
                }
            }
            scanner.close();


            int[] bugIntervals = new int[intervalsList.size()];
            for (int i = 0; i < intervalsList.size(); i++) {
                bugIntervals[i] = intervalsList.get(i);
            }


            Model model = new Model();
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