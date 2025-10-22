import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
public class ReservationServiceTest {
    @Test
    void reserveBookSuccessfully() {
        Book book = new Book("bk1", "Harry Potter", 4);
        IBookRepository bookRepository = new MemoryBookRepository();
        IReservationRepository reservationRepository = new MemoryReservationRepository();

        ReservationService serviceReserve = new ReservationService(bookRepository, reservationRepository);

        bookRepository.save(book);
        serviceReserve.reserve("u1", "bk1");

        assertTrue(reservationRepository.existsByUserAndBook("u1", "bk1"));
        assertEquals(3, bookRepository.findById("bk1").getCopiesAvailable());
    }

    @Test
    void noReserveNoCopiesAvailable() {
        IBookRepository bookRepository = new MemoryBookRepository();
        IReservationRepository reservationRepository = new MemoryReservationRepository();
        Book book = new Book("bk2", "Maugli", 0);
        ReservationService serviceReserve = new ReservationService(bookRepository, reservationRepository);

        bookRepository.save(book);

        assertThrows(IllegalStateException.class, () -> serviceReserve.reserve("u1", "bk2"));
    }
    @Test
    void noReserveSameBook() {
        IBookRepository bookRepository = new MemoryBookRepository();
        IReservationRepository reservationRepository = new MemoryReservationRepository();
        Book book = new Book("bk3", "Atomic Habits", 2);
        ReservationService serviceReserve = new ReservationService(bookRepository, reservationRepository);

        bookRepository.save(book);
        serviceReserve.reserve("u1", "bk3");

        assertThrows(IllegalStateException.class, () -> serviceReserve.reserve("u1", "bk3"));
    }

    @Test
    void reserveLastCopy() {
        Book book = new Book("bk4", "Meditaziya", 1);
        IBookRepository bookRepository = new MemoryBookRepository();
        IReservationRepository reservationRepository = new MemoryReservationRepository();
        ReservationService serviceReserve = new ReservationService(bookRepository, reservationRepository);

        bookRepository.save(book);
        serviceReserve.reserve("u1", "bk4");

        assertEquals(0, bookRepository.findById("bk4").getCopiesAvailable());
        assertTrue(reservationRepository.existsByUserAndBook("u1", "bk4"));
    }

    @Test
    void cancelReservationCopies() {
        IBookRepository bookRepository = new MemoryBookRepository();
        IReservationRepository reservationRepository = new MemoryReservationRepository();
        ReservationService serviceReserve = new ReservationService(bookRepository, reservationRepository);
        Book book = new Book("bk5", "Can't Hurt Me", 1);
        bookRepository.save(book);
        serviceReserve.reserve("u1", "bk5");
        assertEquals(0, bookRepository.findById("bk5").getCopiesAvailable());
        assertTrue(reservationRepository.existsByUserAndBook("u1", "bk5"));
        serviceReserve.cancel("u1", "bk5");
        assertEquals(1, bookRepository.findById("bk5").getCopiesAvailable());
        assertFalse(reservationRepository.existsByUserAndBook("u1", "bk5"));
    }

    @Test
    void listAllReservations() {
        IBookRepository bookRepository = new MemoryBookRepository();
        IReservationRepository reservationRepository = new MemoryReservationRepository();
        ReservationService service = new ReservationService(bookRepository, reservationRepository);
        Book book1 = new Book("bk7", "The Witcher", 2);
        Book book2 = new Book("bk8", "The Power of Now", 2);
        bookRepository.save(book1);
        bookRepository.save(book2);
        service.reserve("u1", "bk7");
        service.reserve("u1", "bk8");
        var reservations = service.listReservations("u1");
        assertEquals(2, reservations.size());
        assertTrue(reservations.stream().anyMatch(r -> r.getBookId().equals("bk7")));
        assertTrue(reservations.stream().anyMatch(r -> r.getBookId().equals("bk8")));

    }

    @Test
    void priorityUserCanReserveEvenWhenNoCopiesAvailable() {
        IBookRepository bookRepository = new MemoryBookRepository();
        IReservationRepository reservationRepository = new MemoryReservationRepository();
        ReservationService service = new ReservationService(bookRepository, reservationRepository);
        Book book = new Book("bk10", "I am Zlatan Ibrahimovic", 0);
        bookRepository.save(book);
        User normalUser = new User("u1", "Andrey", false);
        User priorityUser = new User("u2", "Jasmine", true);
        assertThrows(IllegalStateException.class, () -> service.reserve(normalUser.getId(), book.getId()));
        assertDoesNotThrow(() -> service.reserve(priorityUser.getId(), book.getId()));
    }

    @Test
    void priorityUserBookCopyAvailable() {
        IBookRepository bookRepository = new MemoryBookRepository();
        IReservationRepository reservationRepository = new MemoryReservationRepository();
        ReservationService service = new ReservationService(bookRepository, reservationRepository);
        Book book = new Book("bk11", "Crime and Punishment", 1);
        bookRepository.save(book);
        User normalUser = new User("u1", "Test", false);
        service.reserve(normalUser.getId(), book.getId());
        assertEquals(0, bookRepository.findById(book.getId()).getCopiesAvailable());
        User priorityUser = new User("beastyara", "Peter", true);
        service.reserve(priorityUser.getId(), book.getId());
        service.cancel(normalUser.getId(), book.getId());
        assertTrue(reservationRepository.existsByUserAndBook(priorityUser.getId(), book.getId()));
    }




}