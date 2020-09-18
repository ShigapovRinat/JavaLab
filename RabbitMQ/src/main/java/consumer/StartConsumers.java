package consumer;

public class StartConsumers {
    public static void main(String[] args) {
        new ConsumerHoliday().consume();
        new ConsumerVacation().consume();
    }
}
