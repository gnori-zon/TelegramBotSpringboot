package gnorizon.SpringTestReportsBot.model.Reports.AttributesReport;

public class GeneralInformation {
    private String name;
    private String numberRelease;
    private String readiness;
    private String startTime;
    private String endTime;
    //только в Промедуточном
    private String daysLeft;
    private String note;

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumberRelease() {
        return numberRelease;
    }

    public void setNumberRelease(String numberRelease) {
        this.numberRelease = numberRelease;
    }

    public String getReadiness() {
        return readiness;
    }

    public void setReadiness(String readiness) {
        this.readiness = readiness;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getDaysLeft() {
        return daysLeft;
    }

    public void setDaysLeft(String daysLeft) {
        this.daysLeft = daysLeft;
    }
}
