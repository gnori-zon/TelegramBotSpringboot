package gnorizon.SpringTestReportsBot.repository.Entity.AttributesReport;

import lombok.Data;

@Data
public class GeneralInformation {
    private String name;
    private String numberRelease;
    private String readiness;
    private String startTime;
    private String endTime;
    //только в Промедуточном
    private String daysLeft;
    private String note;
}
