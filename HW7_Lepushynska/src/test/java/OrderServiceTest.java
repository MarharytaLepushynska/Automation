import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {
    @Mock
    BookStrorage storage;

    @Mock
    PaymentService payment;

    @Mock
    NotificationService notification;

    @InjectMocks
    OrderService orderService;

    @Test
    void shouldProcessGoodOrder() {
        BookOrder order = new BookOrder(1L, "Sherlock", 3, "012457665");

        when(storage.checkStock(anyLong(), anyInt())).thenReturn(true);
        when(payment.processPayment(anyDouble(), anyString())).thenReturn(true);

        boolean result = orderService.placeOrder(order);

        assertTrue(result);
    }

    @Test
    void shouldFailWhenProductIsUnavailable() {
        BookOrder order = new BookOrder(2L, "Acotar", 4, "012457665");
        when(storage.checkStock(anyLong(), anyInt())).thenReturn(false);

        boolean result = orderService.placeOrder(order);

        assertFalse(result);
    }

    @Test
    void shouldFailIfCardIsNotValid() {
        BookOrder order = new BookOrder(3L, "Sherlock", 10, "012457679");
        when(storage.checkStock(anyLong(), anyInt())).thenReturn(true);
        when(payment.processPayment(anyDouble(), anyString())).thenReturn(false);

        boolean result = orderService.placeOrder(order);

        assertFalse(result);
    }

    @Test
    void shouldSendNotification() {
        BookOrder order = new BookOrder(4L, "Sherlock", 3, "012457679");

        when(storage.checkStock(anyLong(), anyInt())).thenReturn(true);
        when(payment.processPayment(anyDouble(), anyString())).thenReturn(true);

        orderService.placeOrder(order);

        verify(notification).sendPositiveMessage(order.getTitle(), order.getPrice(), order.getAmount());
    }

    @Test
    void shouldSendNotificationOnce() {
        BookOrder order = new BookOrder(4L, "Sherlock", 3, "012457679");

        when(storage.checkStock(anyLong(), anyInt())).thenReturn(true);
        when(payment.processPayment(anyDouble(), anyString())).thenReturn(true);

        orderService.placeOrder(order);

        verify(notification, times(1)).sendPositiveMessage(order.getTitle(), order.getPrice(), order.getAmount());
    }

    @Test
    void shouldntSentErrorNotification() {
        BookOrder order = new BookOrder(5L, "Sherlock", 3, "012457679");

        when(storage.checkStock(anyLong(), anyInt())).thenReturn(true);
        when(payment.processPayment(anyDouble(), anyString())).thenReturn(true);

        orderService.placeOrder(order);

        verify(notification, never()).sendNotEnoughProductMessage(anyString());
    }

    @Test
    void orderFieldsAreCorrect() {
        BookOrder order = new BookOrder(6L, "Sherlock", 3, "012457665");

        SoftAssertions softly = new SoftAssertions();

        softly.assertThat(order.getTitle())
                .isEqualTo("Sherlock");

        softly.assertThat(order.getId())
                .isEqualTo(6L);

        softly.assertThat(order.getAmount())
                .isPositive();

        softly.assertThat(order.getCardDetails())
                .isEqualTo("012457665");

        softly.assertAll();
    }

    @Test
    void checkOrderList() {
        List<String> titles = List.of("Clean Code", "Physics", "Throne of Glass");

        assertThat(titles)
                .contains("Physics");

        assertThat(titles)
                .containsExactly("Clean Code", "Physics", "Throne of Glass");

        assertThat(titles)
                .endsWith("Throne of Glass");
    }

    @Test
    void checkNormalNumbers() {
        assertEquals(1200, BookStrorage.generateDiscount(6000));
        assertEquals(200, BookStrorage.generateDiscount(2000));
        assertEquals(0, BookStrorage.generateDiscount(500));
    }

    @Test
    void checkAllCases() {
        assertEquals(1200, BookStrorage.generateDiscount(6000));
        assertEquals(500, BookStrorage.generateDiscount(5000));
        assertEquals(200, BookStrorage.generateDiscount(2000));
        assertEquals(0, BookStrorage.generateDiscount(1000));
        assertEquals(0, BookStrorage.generateDiscount(500));
    }
}
