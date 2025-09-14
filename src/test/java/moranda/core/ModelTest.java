package moranda.core;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class ModelTest {

    @Test
    public void testEstimate() {
        var B = 10000;
        var K = 100.;
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

        int n = B / 5;
        var result = m.estimate(Arrays.stream(X).limit(n).toArray());

        assertEquals(B, result.getTotalBugs(), (double) B / 1000);
        assertEquals(X[n], result.getNextBugTime(), X[n] / 1000);
        assertEquals(tk, result.getTotalTestingTime(), tk / 1000);

        int maxRetries = 10;

        for (int r = 0; ;r++) {
            try {
                tk = 0;
                for (int i = 0; i < B; i++) {
                    // Подмешиваем шум
                    var noisyXi = -Math.log(random.nextDouble()) * X[i];
                    rX[i] = (int)Math.round(noisyXi);
                    tk += rX[i];
                }

                result = m.estimate(Arrays.stream(rX).limit(n).toArray());

                assertEquals(B, result.getTotalBugs(), (double) B / 10);
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
        var B = 10000;
        var K = 100.;
        double tk = 0;

        var X = new double[]{1, 2};


        for (int i = 0; i < B; i++) {
            // Скорость поиска багов пропорциональна числу оставшихся багов
            var expectedXi = (B * K / (B - i - 1));
            X[i] = expectedXi;
            tk += expectedXi;
        }

        var m = new Model();

        var result = m.estimate(X);

        assertEquals(0, result.getTotalBugs());
        assertEquals(0, result.getNextBugTime());
        assertEquals(0, result.getTotalTestingTime());
    }
}