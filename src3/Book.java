public abstract class Book {
    private String title;
    private String[] authors;
    private int yearOfPublication;
    private boolean isAvailable;

    public Book(String title, String[] authors, int yearOfPublication, boolean isAvailable) {
        this.title = title;
        this.authors = authors;
        this.yearOfPublication = yearOfPublication;
        this.isAvailable = isAvailable;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String[] getAuthors() {
        return authors;
    }

    public void setAuthors(String[] authors) {
        this.authors = authors;
    }

    public int getYearOfPublication() {
        return yearOfPublication;
    }

    public void setYearOfPublication(int yearOfPublication) {
        this.yearOfPublication = yearOfPublication;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }
}