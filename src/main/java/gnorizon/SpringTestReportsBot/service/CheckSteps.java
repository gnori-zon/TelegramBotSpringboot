package gnorizon.SpringTestReportsBot.service;

import gnorizon.SpringTestReportsBot.service.methodsIO.IOEngine;

import static gnorizon.SpringTestReportsBot.service.TelegramBot.FINISH_TYPE;

public class CheckSteps {
    public static String checkingAndWrite(String message, long chatId, String typeReport){
        try {
            switch (message.charAt(0)) {
                case ('1'):
                    String[] data = message.substring(1).split("-");
                    IOEngine.setCell1(typeReport, data, chatId);
                    if (typeReport.equals(FINISH_TYPE)) {
                        return "2.Введите *дату начала и окончания тестирования* \n\nначиная с 2 " +
                                "XXXн: *2 01.01.2001/02.02.2002*";
                    } else {
                        return "2.Введите *дату начала/окончания/количество оставшихся дней и стенд*  \n\nначиная с 2 " +
                                "XXXн: *2 01.01.2001/02.02.2002/32-имя стенда*";
                    }

                case ('2'):
                    String[] date = message.substring(1).split("/");
                    IOEngine.setCell2(typeReport, date, chatId);
                    if (typeReport.equals(FINISH_TYPE)) {
                        return "3.Введите *имя стенд*  \n\nначиная с 3";
                    } else {
                        return "3.Введите *браузеры-всего тест-кейсов/пройденных тест-кейсов - всего багов/закрых багов* через запятую \n\nначиная с 3" +
                                "XXXн: *3 Chrome-12/6-13/2, Safari-15/2-14/2*";
                    }
                case ('3'):
                    IOEngine.setCell3(typeReport, message, chatId);

                    if (typeReport.equals(FINISH_TYPE)) {
                        return "4.Введите *операционные системы* через запятую \n\nначиная с 4";
                    } else {
                        return "4.Введите *операционные системы/всего тест-кейсов/пройденных тест-кейсов/всего багов/закрых багов* через запятую \n\nначиная с 4" +
                                "XXXн: *4 Windows-12/6-13/2,MacOS-15/2-14/2*";
                    }
                case ('4'):
                    String[] arrayOS = message.substring(1).split(",");
                    IOEngine.setCell4(typeReport, arrayOS, chatId);

                    return "5.Введите *функции и количество багов* в них через запятую  \n\nначиная с 5 " +
                            "XXXн: *5 функция-1,функция-2*";
                case ('5'):
                    String[] arrayFuncs = message.substring(1).split(",");
                    IOEngine.setCell5(typeReport, arrayFuncs, chatId);

                    return "6.Введите *количество всего багов/закрыто багов и всего улучшений/улучшено через* -  \n\nначиная с 6" +
                            "XXXн: *6 15/12-16/13*";
                case ('6'):
                    String[] contBugAndImprove = message.substring(1).split("-");
                    IOEngine.setCell6(typeReport, contBugAndImprove, chatId);

                    return "7.Введите *количество багов/закрыто багов по Приоритету (High,Medium,Low)* через запятую  \n\nначиная с 7" +
                            "XXXн: *7 18/12,16/13,15/2*";
                case ('7'):
                    String[] arrayBugP = message.substring(1).split(",");
                    IOEngine.setCell7(typeReport, arrayBugP, chatId);

                    return "8.Введите *количество багов/закрыто багов по Серьезности (Blocker,Critical,Major,Minor,Trivial)* через запятую  \n\nначиная с 8" +
                            "XXXн: *8 17/16,16/15,15/14,14/13,13/12*";
                case ('8'):
                    String[] arrayBugS = message.substring(1).split(",");
                    IOEngine.setCell8(typeReport, arrayBugS, chatId);

                    return "9.Введите *Модули (общее количесвто тест-кейсов/пройденно)*  через запятую  \n\nначиная с 9 " +
                            "XXXн: *9 Модуль1(11/6),Модуль2(15/1)*";
                case ('9'):
                    String[] arrayModules = message.substring(1).split(",");
                    IOEngine.setCell9(typeReport, arrayModules, chatId);

                    return "0.Введите *Примечание* \n\nначиная с 0 ";
                case ('0'):
                    String note = message.substring(1);
                    IOEngine.setCell0(typeReport, note, chatId);

                    return "SuccessBot.jpg" + "XSXГотово!";
            }
        }catch (Exception e){
                return  "Кажется вы что-то забыли ввести, попробуйте снова";
            }
        return "oups!";
    }
}
