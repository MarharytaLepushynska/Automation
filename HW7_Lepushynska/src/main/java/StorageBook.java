import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StorageBook {
    private Long id;
    private String title;
    private Integer amount;
    private Double price;
}
