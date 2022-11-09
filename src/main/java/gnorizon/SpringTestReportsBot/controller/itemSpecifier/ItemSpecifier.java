package gnorizon.SpringTestReportsBot.controller.itemSpecifier;

import gnorizon.SpringTestReportsBot.model.Reports.Report;

public interface ItemSpecifier {
    String checkingAndWrite(String message, Report report);

}
