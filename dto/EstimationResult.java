package dto;

public class EstimationResult {
    private double totalBugs;
    private double nextBugTime;
    private double totalTestingTime;

    public EstimationResult() {}

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

    public void setTotalBugs(double totalBugs) {
        this.totalBugs = totalBugs;
    }

    public void setNextBugTime(double nextBugTime) {
        this.nextBugTime = nextBugTime;
    }

    public void setTotalTestingTime(double totalTestingTime) {
        this.totalTestingTime = totalTestingTime;
    }
}
