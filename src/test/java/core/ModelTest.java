package core;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class ModelTest {

    @Test
    public void testEstimate() {
        var B = 1253;
        var K = 124565.;
        double tk = 0;

        var X = new double[B];
        var rX = new double[B];

        var random = new Random();

        for (int i = 0; i < B; i++) {
            // Скорость поиска багов пропорциональна числу оставшихся багов
            var expectedXi = (B * K / (B - i - 1));
            X[i] = expectedXi;
            tk += expectedXi;
        }

        var m = new Model();

        int n = B / 10;
        var result = m.estimate(Arrays.stream(X).limit(n).toArray());

        assertEquals(B, result.getTotalBugs(), (double) B / 1000);
        assertEquals(X[n], result.getNextBugTime(), X[n] / 1000);
        assertEquals(tk, result.getTotalTestingTime(), tk / 1000);

        int maxRetries = 1;

        for (int r = 0; ;r++) {
            try {
                var b = 0.;
                var iterations = 0;
                for (int k = 0; k < 10000; k++) {
                    tk = 0;
                    for (int i = 0; i < B; i++) {
                        // Подмешиваем шум
                        var noisyXi = -Math.log(random.nextDouble()) * X[i];
                        rX[i] = (int) Math.round(noisyXi);
                        tk += rX[i];
                    }

                    double newB = m.estimate(Arrays.stream(rX).limit(n).toArray()).getTotalBugs();

                    if (Double.isInfinite(newB)) {
                        continue;
                    }

                    b += newB;
                    iterations++;
                }

                assertEquals(B, b / iterations, (double) B / 10);

                break;
            } catch (AssertionError e) {
                if (r > maxRetries) {
                    throw e;
                }
            }
        }
    }
    @Test
    public void testEstimateVariant5() {
        var X = new double[]{9,12,11,4,7,2,5,8,5,7,1,6,1,9,4,1,3,3,6,1,11,33,7,91,2,1};

        var m = new Model();

        var result = m.estimate(X);

        assertEquals(32, result.getTotalBugs(), 1);
        assertEquals(28, result.getNextBugTime(), 1);
        assertEquals(357, result.getTotalTestingTime(), 1);
    }
}
