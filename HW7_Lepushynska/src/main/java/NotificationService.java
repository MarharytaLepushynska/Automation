public class NotificationService {

    public void sendPositiveMessage(String title, double price, int amount) {
        System.out.println("Your order: " +
                "\nTitle: " + title +
                "\nAmount: " + amount +
                "\nPrice: " + price);
    }

    public void sendNotEnoughProductMessage(String title) {
        System.out.println("We don`t have enough of: " + title);
    }

    public void sendNotValidCardMessage(String card) {
        System.out.println("Your card number " + card + " is not valid");
    }
}
