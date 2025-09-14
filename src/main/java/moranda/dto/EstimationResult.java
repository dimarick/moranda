package moranda.dto;

public class EstimationResult {
    private final double totalBugs;
    private final double nextBugTime;
    private final double totalTestingTime;

    public EstimationResult(double totalBugs, double nextBugTime, double totalTestingTime) {
        this.totalBugs = totalBugs;
        this.nextBugTime = nextBugTime;
        this.totalTestingTime = totalTestingTime;
    }

    public double getTotalBugs() {
        return totalBugs;
    }

    public double getNextBugTime() {
        return nextBugTime;
    }

    public double getTotalTestingTime() {
        return totalTestingTime;
    }
}
