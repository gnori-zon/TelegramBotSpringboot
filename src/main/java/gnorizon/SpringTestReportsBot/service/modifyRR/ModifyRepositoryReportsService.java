package gnorizon.SpringTestReportsBot.service.modifyRR;

import gnorizon.SpringTestReportsBot.repository.Entity.Report;

public interface ModifyRepositoryReportsService {
    void addReport(Report report);

    Report getReport(String name);

    void removeReport(String name);
}
