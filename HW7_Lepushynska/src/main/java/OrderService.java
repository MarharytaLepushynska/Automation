public class OrderService {
    private final BookStrorage storage;
    private final PaymentService payment;
    private final NotificationService notification;

    public OrderService(BookStrorage storage, PaymentService payment, NotificationService notification) {
        this.storage = storage;
        this.payment = payment;
        this.notification = notification;
    }

    public boolean placeOrder(BookOrder order) {
        if (storage.checkStock(order.getId(), order.getAmount())) {

            order.setPrice(storage.countPrice(order.getId(), order.getAmount()));

            if (payment.processPayment(order.getPrice(), order.getCardDetails())) {
                storage.sellBook(order.getId(), order.getAmount());
                notification.sendPositiveMessage(order.getTitle(), order.getPrice(), order.getAmount());
                return true;
            } else {
                notification.sendNotValidCardMessage(order.getCardDetails());
            }
        } else {
            notification.sendNotEnoughProductMessage(order.getTitle());
        }

        return false;
    }
}
