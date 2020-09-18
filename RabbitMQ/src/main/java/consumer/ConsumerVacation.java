package consumer;

import models.UserData;

public class ConsumerVacation extends Consumer {
    @Override
    public String getPackageName() {
        return "vacation/";
    }

    @Override
    public String getText(UserData userData) {
        return "Заявление на отпуск\n " +
                userData.getName() + " " + userData.getSurname() + "\n" +
                "Паспортные данные: " + userData.getPassport() + "\n" +
                "ИНН: " + userData.getInn();
    }
}