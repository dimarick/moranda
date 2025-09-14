package moranda.core;

import moranda.dto.EstimationResult;

import java.util.function.Function;

public class Model {
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
    public EstimationResult estimate(double[] bugIntervals) {

        var B0 = bugIntervals.length;
        var BStep = B0;

        var actualTime = actualTime(bugIntervals);

        var B = solve(
            B0,
            BStep,
            0.01,
            (Double Bj) -> expectedTime(Bj, bugIntervals),
            (Double Bj) -> actualTime
        );

        if (!Double.isFinite(B)) {
            return new EstimationResult(
                Double.POSITIVE_INFINITY,
                Double.POSITIVE_INFINITY,
                Double.POSITIVE_INFINITY
            );
        }

        var K = K(B, bugIntervals);

        var nextBugTime = 1. / K / (B - bugIntervals.length - 1);

        double sumTimes = 0.0;
        for (int i = 1; i <= B - bugIntervals.length; i++) {
            sumTimes += 1. / i;
        }

        var totalTestingTime = (1. / K) * sumTimes;

        return new EstimationResult(
                B,
                nextBugTime,
                totalTestingTime
        );
    }

    private double solve(double B, double BStep, double minBStep, Function<Double, Double> a, Function<Double, Double> b) {
        double prevSign = 0.0;
        double maxValue = 0.0;
        do {
            B += BStep;
            Double aa = a.apply(B);
            Double bb = b.apply(B);
            double newSign = Math.signum(aa - bb);

            if (B > 1e8) {
                return Double.POSITIVE_INFINITY;
            }

            if (newSign == 0.0) {
                return B;
            }

            if (prevSign == 0.0) {
                prevSign = newSign;
            }

            if (prevSign != newSign) {
                maxValue = B;
            }

            if (maxValue > 0.0) {
                BStep *= prevSign * newSign * 0.5;
            } else {
                BStep *= 2;
            }
            prevSign = newSign;
        } while (Math.abs(BStep) > minBStep);

        return B;
    }

    public double expectedTime(double B, double[] X) {
        double sum = 0.0;
        for (int i = 0; i < X.length; i++) {
            sum += 1. / (B - 1 - i);
        }

        return sum / K(B, X);
    }

    public double actualTime(double[] X) {
        double sum = 0.0;
        for (int i = 0; i < X.length; i++) {
            sum += X[i];
        }

        return sum;
    }

    public double K(double B, double[] X) {
        double sum = 0.0;
        for (int i = 0; i < X.length; i++) {
            sum += (B - 1- i) * X[i];
        }

        return X.length / sum;
    }
}
