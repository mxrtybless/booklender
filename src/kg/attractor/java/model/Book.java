package kg.attractor.java.model;

public class Book {
    private int id;
    private String title;
    private String author;
    private String description;
    private String image;
    private boolean issued;
    private Integer issuedTo;

    public Book() {
    }

    public Book(int id,
                String title,
                String author,
                String description,
                String image,
                boolean issued,
                Integer issuedTo) {

        this.id = id;
        this.title = title;
        this.author = author;
        this.description = description;
        this.image = image;
        this.issued = issued;
        this.issuedTo = issuedTo;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getDescription() {
        return description;
    }

    public String getImage() {
        return image;
    }

    public boolean isIssued() {
        return issued;
    }

    public Integer getIssuedTo() {
        return issuedTo;
    }

    public void setIssued(boolean issued) {
        this.issued = issued;
    }

    public void setIssuedTo(Integer issuedTo) {
        this.issuedTo = issuedTo;
    }

    public String getStatusText() {
        return issued ? "Issued" : "Available";
    }
}