package gnorizon.SpringTestReportsBot.model.Reports.AttributesReport;

import java.util.Map;

public class InformationAboutTestObjects {
    // название и количество багов
    private Map<String,Integer> functionalityInformation;
    public Map<String, Integer> getFunctionalityInformation() {
        return functionalityInformation;
    }

    public void setFunctionalityInformation(Map<String, Integer> functionalityInformation) {
        this.functionalityInformation = functionalityInformation;
    }
}
