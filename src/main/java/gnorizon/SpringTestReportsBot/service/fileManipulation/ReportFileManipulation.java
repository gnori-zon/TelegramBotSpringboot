package gnorizon.SpringTestReportsBot.service.fileManipulation;

import gnorizon.SpringTestReportsBot.model.Reports.Report;

public interface ReportFileManipulation  {
    void create(Report report);
    void write(Report report);

    void delete(Report report);
}
