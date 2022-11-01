package gnorizon.SpringTestReportsBot.service.itemSpecifier;

import gnorizon.SpringTestReportsBot.model.Reports.Report;
import gnorizon.SpringTestReportsBot.service.ReportFiller;
import org.springframework.stereotype.Component;

import static gnorizon.SpringTestReportsBot.service.TelegramBot.FINISH_TYPE;

@Component
public class ItemController implements ItemSpecifier{
    @Override
    public String checkingAndWrite(String message, Report report){
        int regex = report.getName().indexOf('_');
        long chatId = Long.parseLong(report.getName().substring(0,regex));
        String typeReport = report.getName().substring(regex+1) ;
        try {
            switch (message.charAt(0)) {
                case ('1'):
                    new ReportFiller(report,typeReport).fillItem1(message);
                    if (typeReport.equals(FINISH_TYPE)) {
                        return "2.Введите *дату начала и окончания тестирования* \n\nначиная с 2 " +
                                "XXXн: *2 01.01.2001/02.02.2002*";
                    } else {
                        return "2.Введите *дату начала/окончания/количество оставшихся дней и стенд*  \n\nначиная с 2 " +
                                "XXXн: *2 01.01.2001/02.02.2002/32-имя стенда*";
                    }

                case ('2'):
                    new ReportFiller(report,typeReport).fillItem2(message);
                    if (typeReport.equals(FINISH_TYPE)) {
                        return "3.Введите *имя стенд*  \n\nначиная с 3";
                    } else {
                        return "3.Введите *браузеры-всего тест-кейсов/пройденных тест-кейсов - всего багов/закрых багов* через запятую \n\nначиная с 3" +
                                "XXXн: *3 Chrome-12/6-13/2, Safari-15/2-14/2*";
                    }
                case ('3'):
                    new ReportFiller(report,typeReport).fillItem3(message);

                    if (typeReport.equals(FINISH_TYPE)) {
                        return "4.Введите *операционные системы* через запятую \n\nначиная с 4";
                    } else {
                        return "4.Введите *операционные системы/всего тест-кейсов/пройденных тест-кейсов/всего багов/закрых багов* через запятую \n\nначиная с 4" +
                                "XXXн: *4 Windows-12/6-13/2,MacOS-15/2-14/2*";
                    }
                case ('4'):
                    new ReportFiller(report,typeReport).fillItem4(message);
                    return "5.Введите *функции и количество багов* в них через запятую  \n\nначиная с 5 " +
                            "XXXн: *5 функция-1,функция-2*";
                case ('5'):
                    new ReportFiller(report,typeReport).fillItem5(message);

                    return "6.Введите *количество всего багов/закрыто багов и всего улучшений/улучшено через* -  \n\nначиная с 6" +
                            "XXXн: *6 15/12-16/13*";
                case ('6'):
                    new ReportFiller(report,typeReport).fillItem6(message);

                    return "7.Введите *количество багов/закрыто багов по Приоритету (High,Medium,Low)* через запятую  \n\nначиная с 7" +
                            "XXXн: *7 18/12,16/13,15/2*";
                case ('7'):
                    new ReportFiller(report,typeReport).fillItem7(message);

                    return "8.Введите *количество багов/закрыто багов по Серьезности (Blocker,Critical,Major,Minor,Trivial)* через запятую  \n\nначиная с 8" +
                            "XXXн: *8 17/16,16/15,15/14,14/13,13/12*";
                case ('8'):
                    new ReportFiller(report,typeReport).fillItem8(message);

                    return "9.Введите *Модули (общее количесвто тест-кейсов/пройденно)*  через запятую  \n\nначиная с 9 " +
                            "XXXн: *9 Модуль1(11/6),Модуль2(15/1)*";
                case ('9'):
                    new ReportFiller(report,typeReport).fillItem9(message);

                    return "0.Введите *Примечание* \n\nначиная с 0 ";
                case ('0'):
                    new ReportFiller(report,typeReport).fillItem0(message);

                    return "SuccessBot.jpg" + "XSXГотово!";
            }
        }catch (Exception e){
            return  "Кажется вы что-то забыли ввести, попробуйте снова";
        }
        return "oups!";
    }
}
