package gnorizon.SpringTestReportsBot.model.Reports;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
@Component
public class RepositoryReports {
    private Map<String,Report> reports = new HashMap<>();

    public void addReport(Report report){
        reports.put(report.getName(),report);
    }

    public Report getReport(String name){
        return reports.get(name);
    }

    public void removeReport(String name){
        reports.remove(name);
    }

}
