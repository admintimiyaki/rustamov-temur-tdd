public class Reservation {
    private String userId;
    private String bookId;

    public Reservation(String userId, String bookId) {
        this.userId = userId;
        this.bookId = bookId;
    }

    public String getUserId() {
        return this.userId;
    }

    public String setUserId(String userId){
        return this.userId = userId;
    }

    public String getBookId() {
        return this.bookId;
    }

    public String setBookId(String bookId){
        return this.bookId = bookId;
    }
}
