public class Book {
    private String title;
    private long amount;
    private double price;

    Book(String title) {
        this.title = title;
        amount = 0L;
    }

    Book(String title, long amount) {
        this.title = title;
        this.amount = amount;
    }

    public long getAmount() {
        return amount;
    }

    public String getTitle() {
        return title;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }
}
