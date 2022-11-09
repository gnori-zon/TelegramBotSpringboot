package gnorizon.SpringTestReportsBot.repository;

import gnorizon.SpringTestReportsBot.repository.Entity.Report;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
/**
 * {@link Repository} for {@link Report} entity.
 */
@Repository
public class RepositoryReports {
    private Map<String, Report> reports = new HashMap<>();

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
