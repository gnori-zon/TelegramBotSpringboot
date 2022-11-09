package gnorizon.SpringTestReportsBot.service.fileManipulation;

import gnorizon.SpringTestReportsBot.repository.Entity.Report;

/**
 * Service for working with a file in memory
 */
public interface ReportFileManipulationService {
    void create(Report report);
    void write(Report report);

    void delete(Report report);
}
