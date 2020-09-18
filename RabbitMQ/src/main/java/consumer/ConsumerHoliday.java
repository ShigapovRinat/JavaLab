package consumer;

import models.UserData;

public class ConsumerHoliday extends Consumer {
    @Override
    public String getPackageName() {
        return "holiday/";
    }

    @Override
    public String getText(UserData userData) {
        return "Заявление на академические каникулы\n " +
                userData.getName() + " " + userData.getSurname() + "\n" +
                "Паспортные данные: " + userData.getPassport() + "\n" +
                "ИНН: " + userData.getInn();
    }
}
