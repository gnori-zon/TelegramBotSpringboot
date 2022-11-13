package gnorizon.SpringTestReportsBot.repository.Entity.AttributesReport;

import lombok.Data;

@Data
public class InformationAboutBugs {
    // opened and closed | открытые и закрытые
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

}