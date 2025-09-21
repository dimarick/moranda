package core;

import java.util.function.Function;

public class Model {

    public static final double MAX_POSSIBLE_BUG_COUNT = 1e6;

    /**
     * Модель Джелинского-Моранды – одна из первых и наиболее простых моделей классического типа. Модель использовалась при разработке ПО для весьма ответственных проектов, в частности для ряда модулей программы Apollo. В ее основу были положены следующие допущения:
     *     1) функция риска или иначе – интенсивность обнаружения ошибок R(t) пропорциональна текущему числу ошибок в программе, т.е. числу оставшихся (первоначальных) ошибок минус обнаруженные;
     *     2) все ошибки одинаково вероятны и их появление не зависит друг от друга;
     *     3) каждая ошибка имеет один и тот же порядок серьезности;
     *     4) время до следующего отказа распределено экспоненциально;
     *     5) ПО функционирует в среде, близкой к реальным условиям;
     *     6) ошибки постоянно корректируются без внесения в ПО новых;
     *     7) R(t)=const в интервале между двумя смежными моментами появления ошибок.
     */
    public dto.EstimationResult estimate(double[] bugIntervals) {
        var actualTime = actualTime(bugIntervals);

        var B = solve(
            bugIntervals.length + 1,
            0.01,
            (Double Bj) -> expectedTime(Bj, bugIntervals),
            (Double) -> actualTime
        );

        var K = K(B, bugIntervals);

        var nextBugTime = 0.0;

        if (B >= bugIntervals.length) {
            nextBugTime = 1. / K / (B - bugIntervals.length - 1);
        }

        if (B >= MAX_POSSIBLE_BUG_COUNT) {
            return new dto.EstimationResult(
                Double.POSITIVE_INFINITY,
                nextBugTime,
                Double.POSITIVE_INFINITY
            );
        }

        double sumTimes = 0.0;
        for (int i = 1; i <= B - bugIntervals.length; i++) {
            sumTimes += 1. / i;
        }

        var totalTestingTime = (1. / K) * sumTimes;

        return new dto.EstimationResult(
            B,
            nextBugTime,
            totalTestingTime
        );
    }

    private double solve(double initialX, double minXStep, Function<Double, Double> a, Function<Double, Double> b) {
        var Xmin = initialX;
        var Xmax = MAX_POSSIBLE_BUG_COUNT;
        var leftA = a.apply(Xmin);
        var leftB = b.apply(Xmin);
        var rightA = a.apply(Xmax);
        var rightB = b.apply(Xmax);
        var leftSign = Math.signum(leftA - leftB);
        var rightSign = Math.signum(rightA - rightB);

        do {
            if (leftSign == rightSign) {
                return Xmax;
            }

            var Xmid = 0.5 * (Xmax + Xmin);
            var midA = a.apply(Xmid);
            var midB = b.apply(Xmid);

            var midSign = Math.signum(midA - midB);

            if (midSign != leftSign) {
                Xmax = Xmid;
                rightSign = midSign;
            } else {
                Xmin = Xmid;
                leftSign = midSign;
            }
        } while (Xmax - Xmin > minXStep);

        return 0.5 * (Xmax + Xmin);
    }

    public double expectedTime(double B, double[] X) {
        double sum = 0.0;
        for (int i = 0; i < X.length; i++) {
            sum += 1. / (B - i);
        }

        return sum / K(B, X);
    }

    public double actualTime(double[] X) {
        double sum = 0.0;
        for (double x : X) {
            sum += x;
        }

        return sum;
    }

    public double K(double B, double[] X) {
        double sum = 0.0;
        for (int i = 0; i < X.length; i++) {
            sum += (B - i) * X[i];
        }

        return X.length / sum;
    }
}
