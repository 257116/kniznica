public class Novel extends Book {
    private String genre;

    public Novel(String title, String[] authors, int yearOfPublication, boolean isAvailable, String genre) {
        super(title, authors, yearOfPublication, isAvailable);
        this.genre = genre;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }
}