package gnorizon.SpringTestReportsBot.controller.itemSpecifier.items;

import gnorizon.SpringTestReportsBot.repository.Entity.Report;

public interface Item {
    String execute(String message, Report report);
}
