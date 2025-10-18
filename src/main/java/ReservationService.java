import java.util.List;
public class ReservationService {

    private final IBookRepository bookRepository;
    private final IReservationRepository reservationRepository;

    public ReservationService(IBookRepository bookRepository, IReservationRepository reservationRepository) {
        this.bookRepository = bookRepository;
        this.reservationRepository = reservationRepository;
    }

    public void reserve(String userId, String bookId) {
        Book book = bookRepository.findById(bookId);
        if (book.getCopiesAvailable() <= 0) {
            throw new IllegalStateException("No copies!");
        }
        if (reservationRepository.existsByUserAndBook(userId, bookId)) {
            throw new IllegalStateException("This is already reserved book");
        }
        Reservation reservation = new Reservation(userId, bookId);
        reservationRepository.save(reservation);
        int updatedCopies = book.getCopiesAvailable() - 1;
        book.setCopiesAvailable(updatedCopies);
        bookRepository.save(book);
    }

    public void cancel(String userId, String bookId) {
        if (!reservationRepository.existsByUserAndBook(userId, bookId)) {
            throw new IllegalArgumentException("No such reservation!");
        }
        reservationRepository.delete(userId, bookId);
        Book book = bookRepository.findById(bookId);
        book.setCopiesAvailable(book.getCopiesAvailable() + 1);
        bookRepository.save(book);
    }

    public List<Reservation> listReservations(String userId) {
        return reservationRepository.findByUser(userId);
    }


}
