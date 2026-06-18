import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BookOrder {
    private Long id;
    private String title;
    private Integer amount;
    private Double price;
    private String cardDetails;

    public BookOrder(Long id, String title, Integer amount, String cardDetails) {
        this.id = id;
        this.title = title;
        this.amount = amount;
        this.cardDetails = cardDetails;
    }
}
