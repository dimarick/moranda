package core;

import dto.EstimationResult;
public class Model {
    public EstimationResult estimate(double[] bugIntervals) {

            return new EstimationResult(0, 0, 0);

    }

    private double calculateAverageInterval(double[] intervals) {
        if (intervals.length == 0) return 0;

        long sum = 0;
        for (double interval : intervals) {
            sum += (long) interval;
        }
        return (double) (sum / intervals.length);
    }
}
