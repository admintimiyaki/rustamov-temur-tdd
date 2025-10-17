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
}
