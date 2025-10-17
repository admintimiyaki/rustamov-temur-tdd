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
            throw new IllegalStateException("No copies available");
        }
        Reservation reservation = new Reservation(userId, bookId);
        reservationRepository.save(reservation);
        int updatedCopies = book.getCopiesAvailable() - 1;
        book.setCopiesAvailable(updatedCopies);
        bookRepository.save(book);
    }


}
