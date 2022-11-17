package gnorizon.SpringTestReportsBot.controller.itemSpecifier.items;

import gnorizon.SpringTestReportsBot.repository.Entity.Report;

public class Item10 implements Item{
    public static String imagePath = "src/main/resources/static/SuccessBot.jpg";
    public static String regex = "XSX";
    public static String returnText = "Готово!";
    private final String LAST_TEXT = imagePath+regex+returnText;
    @Override
    public String execute(String message, Report report) {
        String note = message.substring(1);

        if (note.charAt(0)==' '){
            note = note.substring(1);
        }
        report.generalInformation.setNote(note);
        return LAST_TEXT ;
    }
}
