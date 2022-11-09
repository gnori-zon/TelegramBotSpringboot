package gnorizon.SpringTestReportsBot.service.modifyRR;

import gnorizon.SpringTestReportsBot.model.Reports.Report;
import gnorizon.SpringTestReportsBot.repository.RepositoryReports;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ModifyRepositoryReportsServiceImpl implements ModifyRepositoryReportsService{
    RepositoryReports reports;
    @Autowired
    public ModifyRepositoryReportsServiceImpl(RepositoryReports reports) {
        this.reports = reports;
    }

    @Override
    public void addReport(Report report) {
        reports.addReport(report);
    }

    @Override
    public Report getReport(String name) {
        return reports.getReport(name);
    }

    @Override
    public void removeReport(String name) {
        reports.removeReport(name);
    }
}
