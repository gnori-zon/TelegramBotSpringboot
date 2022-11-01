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

    public void setTotalBugs(String[] totalBugs) {
        this.totalBugs = Arrays.stream(totalBugs).mapToInt(Integer::parseInt).toArray();
    }

    public int[] getTotalImprovements() {
        return totalImprovements;
    }

    public void setTotalImprovements(String[] totalImprovements) {
        this.totalImprovements = Arrays.stream(totalImprovements).mapToInt(Integer::parseInt).toArray();
    }

    public int[] getBlockers() {
        return blockers;
    }

    public void setBlockers(String[] blockers) {
        this.blockers = Arrays.stream(blockers).mapToInt(Integer::parseInt).toArray();
    }

    public int[] getCritical() {
        return critical;
    }

    public void setCritical(String[] critical) {
        this.critical = Arrays.stream(critical).mapToInt(Integer::parseInt).toArray();
    }

    public int[] getMajors() {
        return majors;
    }

    public void setMajors(String[] majors) {
        this.majors = Arrays.stream(majors).mapToInt(Integer::parseInt).toArray();
    }

    public int[] getMinors() {
        return minors;
    }

    public void setMinors(String[] minors) {
        this.minors = Arrays.stream(minors).mapToInt(Integer::parseInt).toArray();
    }

    public int[] getTrivial() {
        return trivial;
    }

    public void setTrivial(String[] trivial) {
        this.trivial = Arrays.stream(trivial).mapToInt(Integer::parseInt).toArray();
    }

    public int[] getHigh() {
        return high;
    }

    public void setHigh(String[] high) {
        this.high = Arrays.stream(high).mapToInt(Integer::parseInt).toArray();
    }

    public int[] getMedium() {
        return medium;
    }

    public void setMedium(String[] medium) {
        this.medium = Arrays.stream(medium).mapToInt(Integer::parseInt).toArray();
    }

    public int[] getLow() {
        return low;
    }

    public void setLow(String[] low) {
        this.low = Arrays.stream(low).mapToInt(Integer::parseInt).toArray();
    }
}
