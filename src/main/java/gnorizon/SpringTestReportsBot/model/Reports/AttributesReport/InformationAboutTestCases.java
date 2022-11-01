package gnorizon.SpringTestReportsBot.model.Reports.AttributesReport;

import java.util.List;
import java.util.Map;
public class InformationAboutTestCases {
    // map<имя модуля, list<всего, закрыто>>
    Map<String, List<Integer>> informationAboutModules;


    public Map<String, List<Integer>> getInformationAboutModules() {
        return informationAboutModules;
    }

    public void setInformationAboutModules(Map<String, List<Integer>> informationAboutModules) {
        this.informationAboutModules = informationAboutModules;
    }

}
