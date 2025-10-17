public class ReservationService {

    private final IBookRepository bookRepository;
    private final IReservationRepository reservationRepository;

    public ReservationService(IBookRepository bookRepository, IReservationRepository reservationRepository) {
        this.bookRepository = bookRepository;
        this.reservationRepository = reservationRepository;
    }

    public void reserve(String userId, String bookId) {
        Book book = bookRepository.findById(bookId);
        reservationRepository.save(new Reservation(userId, bookId));
        book.setCopiesAvailable(book.getCopiesAvailable() - 1);
        bookRepository.save(book);
    }
}
