import java.util.ArrayList;
import java.util.List;

public class BookStrorage {
   private List<StorageBook> storage;

   public BookStrorage() {
       storage = new ArrayList<>();
   }

   public boolean checkStock(long id, int amount) {
       StorageBook foundBook = null;
       for (StorageBook book: storage) {
           if (book.getId() == id) {
               foundBook = book;
               break;
           }
       }

       return foundBook != null && foundBook.getAmount() >= amount;
   }

   public double countPrice(long id, int amount) {
       double orderPrice = 0;

       for (StorageBook book: storage) {
           orderPrice = amount * book.getPrice();
       }

       return orderPrice;
   }

   public static double generateDiscount(double price) {
       double discount = 0;

       if (price > 5000) {
           discount = price * 0.2;
       } else if (price > 1000){
           discount = price * 0.1;
       }

       return discount;
   }

   public void sellBook(long id, int amount) {
       for (StorageBook book: storage) {
           if (book.getId() == id) {
               int newAmount = book.getAmount() - amount;
               book.setAmount(newAmount);
               break;
           }
       }
   }
}
