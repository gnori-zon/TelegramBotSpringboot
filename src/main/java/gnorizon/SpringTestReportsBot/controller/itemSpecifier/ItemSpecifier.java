package gnorizon.SpringTestReportsBot.controller.itemSpecifier;

import gnorizon.SpringTestReportsBot.repository.Entity.Report;

public interface ItemSpecifier {
    String checkingAndWrite(String message, Report report);

}
