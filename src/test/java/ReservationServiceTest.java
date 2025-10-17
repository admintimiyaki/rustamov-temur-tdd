import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
public class ReservationServiceTest {
    @Test
    void reserveBookSuccessfully() {
        IBookRepository bookRepository = new MemoryBookRepository();
        IReservationRepository reservationRepository = new MemoryReservationRepository();
        ReservationService serviceReserve = new ReservationService(bookRepository, reservationRepository);
        Book book = new Book("bk1", "Harry Potter", 4);
        bookRepository.save(book);
        serviceReserve.reserve("u1", "bk1");

        assertTrue(reservationRepository.existsByUserAndBook("u1", "bk1"));
        assertEquals(3, bookRepository.findById("bk1").getCopiesAvailable());
    }

    @Test
    void noReserveNoCopiesAvailable() {
        IBookRepository bookRepository = new MemoryBookRepository();
        IReservationRepository reservationRepository = new MemoryReservationRepository();
        ReservationService serviceReserve = new ReservationService(bookRepository, reservationRepository);
        Book book = new Book("bk2", "Mowgli", 0);
        bookRepository.save(book);
        assertThrows(IllegalStateException.class, () -> {serviceReserve.reserve("u1", "bk2");});
    }
}
