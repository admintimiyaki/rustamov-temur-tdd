public class User {
    private String id;
    private String name;
    private boolean priority; // new field

    public User(String id, String name) {
        this(id, name, false);
    }

    public User(String id, String name, boolean priority) {
        this.id = id;
        this.name = name;
        this.priority = priority;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isPriority() {
        return priority;
    }

    public void setPriority(boolean priority) {
        this.priority = priority;
    }
}
