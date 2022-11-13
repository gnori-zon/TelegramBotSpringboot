package gnorizon.SpringTestReportsBot.controller.itemSpecifier.items;

import gnorizon.SpringTestReportsBot.repository.Entity.Report;

public class NoItem implements Item{
    @Override
    public String execute(String message, Report report) {
        return "oups!";
    }
}
