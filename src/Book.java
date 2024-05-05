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

/*
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

class Book {
    private String title;
    private String[] authors;
    private String genre;
    private int yearOfPublication;
    private boolean isAvailable;

    public Book(String title, String[] authors, String genre, int yearOfPublication, boolean isAvailable) {
        this.title = title;
        this.authors = authors;
        this.genre = genre;
        this.yearOfPublication = yearOfPublication;
        this.isAvailable = isAvailable;
    }

    // Getters and setters

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

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
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

    @Override
    public String toString() {
        return "Book{" +
                "title='" + title + '\'' +
                ", authors=" + String.join(", ", authors) +
                ", genre='" + genre + '\'' +
                ", yearOfPublication=" + yearOfPublication +
                ", isAvailable=" + isAvailable +
                '}';
    }
}
 */