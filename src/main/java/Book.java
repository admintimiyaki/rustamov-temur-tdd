public class Book {
    private String id;
    private String title;
    private int copiesAvailable;

    public Book(String id, String title, int copiesAvailable) {
        this.id = id;
        this.title = title;
        this.copiesAvailable = copiesAvailable;
    }

    public int getCopiesAvailable() {
        return this.copiesAvailable;
    }

    public void setCopiesAvailable(int copiesAvailable) {
        this.copiesAvailable = copiesAvailable;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
