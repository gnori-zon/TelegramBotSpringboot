package gnorizon.SpringTestReportsBot.model.Reports.AttributesReport;

import java.util.Arrays;

public class InformationAboutBugs {
    // opened and closed
    private int [] totalBugs;
    private int [] totalImprovements;

    private int [] blockers;
    private int [] critical;
    private int [] majors;
    private int [] minors;
    private int [] trivial;

    private int [] high;
    private int [] medium;
    private int [] low;

    public int[] getTotalBugs() {
        return totalBugs;
    }

    public void setTotalBugs(int[] totalBugs) {
        this.totalBugs = totalBugs;
    }

    public int[] getTotalImprovements() {
        return totalImprovements;
    }

    public void setTotalImprovements(int[] totalImprovements) {
        this.totalImprovements = totalImprovements;
    }

    public int[] getBlockers() {
        return blockers;
    }

    public void setBlockers(int[] blockers) {
        this.blockers = blockers;
    }

    public int[] getCritical() {
        return critical;
    }

    public void setCritical(int[] critical) {
        this.critical = critical;
    }

    public int[] getMajors() {
        return majors;
    }

    public void setMajors(int[] majors) {
        this.majors = majors;
    }

    public int[] getMinors() {
        return minors;
    }

    public void setMinors(int[] minors) {
        this.minors = minors;
    }

    public int[] getTrivial() {
        return trivial;
    }

    public void setTrivial(int[] trivial) {
        this.trivial = trivial;
    }

    public int[] getHigh() {
        return high;
    }

    public void setHigh(int[] high) {
        this.high = high;
    }

    public int[] getMedium() {
        return medium;
    }

    public void setMedium(int[] medium) {
        this.medium = medium;
    }

    public int[] getLow() {
        return low;
    }

    public void setLow(int[] low) {
        this.low = low;
    }

}