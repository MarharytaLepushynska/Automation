import java.util.ArrayList;
import java.util.List;

public class PaymentService {
    private List<String> validCards;

    public PaymentService() {
        validCards = new ArrayList<>();
        validCards.add("012457665");
        validCards.add("5865440122");
    }

    public boolean processPayment(double price, String cardDetails) {
        if (validCards.contains(cardDetails)) {
            System.out.println("Chech: " +
                    "\nPrice: " + price + " грн");

             return true;
        }

        return false;
    }
}
