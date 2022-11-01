package gnorizon.SpringTestReportsBot.model.Reports;

import gnorizon.SpringTestReportsBot.model.Reports.AttributesReport.*;

public class Report {
    private String name;// chatId_typeReport
    public GeneralInformation generalInformation;
    public Environment environment;
    public InformationAboutBugs aboutBugs;
    public InformationAboutTestCases aboutTestCases;
    public InformationAboutTestObjects aboutTestObjects;

    public Report(String name) {
        this.name = name;
        generalInformation = new GeneralInformation();
        environment = new Environment();
        aboutBugs = new InformationAboutBugs();
        aboutTestCases = new InformationAboutTestCases();
        aboutTestObjects = new InformationAboutTestObjects();
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Report report = (Report) o;

        return name.equals(report.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
