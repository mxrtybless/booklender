package kg.attractor.java.model;

public class Book {
    private int id;
    private String title;
    private String author;
    private String image;
    private boolean issued;
    private Integer issuedTo;

    public Book(int id, String title, String author, String image, boolean issued, Integer issuedTo) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.image = image;
        this.issued = issued;
        this.issuedTo = issuedTo;
    }

    public int getId() { return id; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public String getImage() { return image; }
    public boolean isIssued() { return issued; }
    public Integer getIssuedTo() { return issuedTo; }
}