package gnorizon.SpringTestReportsBot.config;

import gnorizon.SpringTestReportsBot.controller.itemSpecifier.ItemController;

/**
 * Enumeration for controller {@link ItemController}'s.
 */
public enum Items {
    ITEM_1('1',"Введите *название отчета,релиз и готовность* через запятую \n\nначиная с 1"+
            "\nн: *1 Имя-3-готов*" ),
    ITEM_2('2', "2.Введите *дату начала и окончания тестирования* \n\nначиная с 2 XXXн: *2 01.01.2001/02.02.2002*",
            "2.Введите *дату начала/окончания/количество оставшихся дней и стенд*  \n\nначиная с 2 " +
                    "XXXн: *2 01.01.2001/02.02.2002/32-имя стенда*"),
    ITEM_3('3',"3.Введите *имя стенд*  \n\nначиная с 3",
            "3.Введите *браузеры-всего тест-кейсов/пройденных тест-кейсов - всего багов/закрых багов* через запятую \n\nначиная с 3" +
                    "XXXн: *3 Chrome-12/6-13/2, Safari-15/2-14/2*"),
    ITEM_4('4', "4.Введите *операционные системы* через запятую \n\nначиная с 4",
            "4.Введите *операционные системы/всего тест-кейсов/пройденных тест-кейсов/всего багов/закрых багов* через запятую \n\nначиная с 4" +
                    "XXXн: *4 Windows-12/6-13/2,MacOS-15/2-14/2*"),
    ITEM_5('5', "5.Введите *функции и количество багов* в них через запятую  \n\nначиная с 5 " +
            "XXXн: *5 функция1-1,функция2-2*"),
    ITEM_6('6', "6.Введите *количество всего багов/закрыто багов и всего улучшений/улучшено через* -  \n\nначиная с 6" +
            "XXXн: *6 15/12-16/13*"),
    ITEM_7('7', "7.Введите *количество багов/закрыто багов по Приоритету (High,Medium,Low)* через запятую  \n\nначиная с 7" +
            "XXXн: *7 18/12,16/13,15/2*"),
    ITEM_8('8', "8.Введите *количество багов/закрыто багов по Серьезности (Blocker,Critical,Major,Minor,Trivial)* через запятую  \n\nначиная с 8" +
            "XXXн: *8 17/16,16/15,15/14,14/13,13/12*"),
    ITEM_9('9', "9.Введите *Модули (общее количесвто тест-кейсов/пройденно)*  через запятую  \n\nначиная с 9 " +
            "XXXн: *9 Модуль1(11/6),Модуль2(15/1)*"),
    ITEM_10('0', "0.Введите *Примечание* \n\nначиная с 0 "),;
    public Character step;
    public String textForStep;
    public String textForNotFinalRep;
    Items(Character step,String textForStep){
        this.step = step;
        this.textForStep = textForStep;
    }
    Items(Character step, String textForStep, String textForNotFinalRep){
        this.step = step;
        this.textForStep = textForStep;
        this.textForNotFinalRep = textForNotFinalRep;
    }
}
