package gnorizon.SpringTestReportsBot.model.Reports.AttributesReport;

import java.util.List;
import java.util.Map;

public class Environment{
    private String standName;
    private String [] OSNames;
    // для Промежуточного  Map<Название,List<Всего,Закрыто>>
    private Map<String,List<Integer>> OSInformation;
    private Map<String,List<Integer>> BrowsersInformation;



    public String getStandName() {
        return standName;
    }

    public void setStandName(String standName) {
        this.standName = standName;
    }

    public String[] getOSNames() {
        return OSNames;
    }

    public void setOSNames(String[] OSNames) {
        this.OSNames = OSNames;
    }

    public Map<String, List<Integer>> getOSInformation() {
        return OSInformation;
    }

    public void setOSInformation(Map<String, List<Integer>> OSInformation) {
        this.OSInformation = OSInformation;
    }

    public Map<String, List<Integer>> getBrowsersInformation() {
        return BrowsersInformation;
    }

    public void setBrowsersInformation(Map<String, List<Integer>> browsersInformation) {
        BrowsersInformation = browsersInformation;
    }
}
