package core;

import dto.EstimationResult;
public class Model {
    public EstimationResult estimate(int[] bugIntervals) {
        if (bugIntervals == null || bugIntervals.length == 0) {
            return new EstimationResult(0, 0, 0);
        }



        int detectedBugs = bugIntervals.length;
        int estimatedTotalBugs = (int) (detectedBugs * 1.5) + 1; // Предполагаем, что есть еще ошибки
        int averageInterval = calculateAverageInterval(bugIntervals);
        int nextBugTime = averageInterval;
        int totalTestingTime = averageInterval * (estimatedTotalBugs - detectedBugs);

        return new EstimationResult(estimatedTotalBugs, nextBugTime, totalTestingTime);
    }

    private int calculateAverageInterval(int[] intervals) {
        if (intervals.length == 0) return 0;

        long sum = 0;
        for (int interval : intervals) {
            sum += interval;
        }
        return (int) (sum / intervals.length);
    }
}
