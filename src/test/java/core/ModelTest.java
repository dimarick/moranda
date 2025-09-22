package core;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class ModelTest {

    @Test
    public void testEstimate() {
        var B = 200;
        var K = 1.;
        double tk = 0;

        var X = new double[B];

        for (int i = 0; i < B; i++) {
            // Скорость поиска багов пропорциональна числу оставшихся багов
            var expectedXi = (B * K / (B - i));
            X[i] = expectedXi;
            tk += expectedXi;
        }

        var m = new Model();

        int n = B / 10;
        var part = Arrays.stream(X).limit(n).toArray();
        var result = m.estimate(part);
        var partTime = Arrays.stream(part).sum();

        assertEquals(B, result.getTotalBugs(), (double) B / 10000);
        assertEquals(X[n + 1], result.getNextBugTime(), X[n + 1] / 10000);
        assertEquals(tk, partTime + result.getTotalTestingTime(), tk / 10000);
    }
    @Test
    public void testEstimateVariant5() {
        var X = new double[]{9,12,11,4,7,2,5,8,5,7,1,6,1,9,4,1,3,3,6,1,11,33,7,91,2,1};

        var m = new Model();

        var result = m.estimate(X);

        assertEquals(32, result.getTotalBugs(), 1);
        assertEquals(34, result.getNextBugTime(), 1);
        assertEquals(333, result.getTotalTestingTime(), 1);
    }
    @Test
    public void testEstimateCase1() {
        var X = new double[]{4,4,4,4};

        var m = new Model();

        var result = m.estimate(X);

        assertEquals(Double.POSITIVE_INFINITY, result.getTotalBugs(), 1);
        assertEquals(4, result.getNextBugTime(), 1);
        assertEquals(Double.POSITIVE_INFINITY, result.getTotalTestingTime(), 1);
    }
    @Test
    public void testEstimateCase2() {
        var X = new double[]{4,3,2,1};

        var m = new Model();

        var result = m.estimate(X);

        assertEquals(Double.POSITIVE_INFINITY, result.getTotalBugs(), 1);
        assertEquals(2.5, result.getNextBugTime(), 1);
        assertEquals(Double.POSITIVE_INFINITY, result.getTotalTestingTime(), 1);
    }
}
