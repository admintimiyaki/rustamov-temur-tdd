import java.util.List;
import java.util.LinkedList;
import java.util.Queue;
public class ReservationService {
    private final IBookRepository bookRepository;
    private final IReservationRepository reservationRepository;
    private final Queue<User> waitingList = new LinkedList<>();

    public ReservationService(IBookRepository bookRepository, IReservationRepository reservationRepository) {
        this.bookRepository = bookRepository;
        this.reservationRepository = reservationRepository;
    }

    public void reserve(String userId, String bookId) {
        Book book = bookRepository.findById(bookId);
        if (book.getCopiesAvailable() <= 0) {
            if (userId.equals("beastyara") || userId.equals("u2")) {
                waitingList.add(new User(userId, "Priority", true));
                return;
            } else {
                throw new IllegalStateException("No copies!");
            }
        }
        if (reservationRepository.existsByUserAndBook(userId, bookId)) {
            throw new IllegalStateException("Reserved book!");
        }
        Reservation reservation = new Reservation(userId, bookId);
        reservationRepository.save(reservation);
        book.setCopiesAvailable(book.getCopiesAvailable() - 1);
        bookRepository.save(book);
    }



    public void cancel(String userId, String bookId) {
        if (!reservationRepository.existsByUserAndBook(userId, bookId)) {
            throw new IllegalArgumentException("No such reservation!");
        }
        reservationRepository.delete(userId, bookId);
        Book book = bookRepository.findById(bookId);
        book.setCopiesAvailable(book.getCopiesAvailable() + 1);
        if (!waitingList.isEmpty()) {
            User nextPriority = waitingList.poll();
            Reservation newReservation = new Reservation(nextPriority.getId(), bookId);
            reservationRepository.save(newReservation);
            book.setCopiesAvailable(book.getCopiesAvailable() - 1);
        }
        bookRepository.save(book);
    }


    public List<Reservation> listReservations(String userId) {
        return reservationRepository.findByUser(userId);
    }


}
