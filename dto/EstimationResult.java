package dto;

public class EstimationResult {
    private int totalBugs;
    private int nextBugTime;
    private int totalTestingTime;

    public EstimationResult() {}

    public EstimationResult(int totalBugs, int nextBugTime, int totalTestingTime) {
        this.totalBugs = totalBugs;
        this.nextBugTime = nextBugTime;
        this.totalTestingTime = totalTestingTime;
    }

    public int getTotalBugs() {
        return totalBugs;
    }

    public int getNextBugTime() {
        return nextBugTime;
    }

    public int getTotalTestingTime() {
        return totalTestingTime;
    }

    public void setTotalBugs(int totalBugs) {
        this.totalBugs = totalBugs;
    }

    public void setNextBugTime(int nextBugTime) {
        this.nextBugTime = nextBugTime;
    }

    public void setTotalTestingTime(int totalTestingTime) {
        this.totalTestingTime = totalTestingTime;
    }
}
