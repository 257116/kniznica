import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.sql.DriverManager;


public class Main {
    private static List<Book> bookList = new ArrayList<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        //check conn
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            System.out.println("SQLite JDBC driver not found.");
            e.printStackTrace();
            return;
        }

        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:library.db");
            // Use the connection to save data to the SQL database
            // ...
        } catch (SQLException e) {
            System.out.println("Error saving data to SQL database: " + e.getMessage());
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    System.out.println("Error closing SQL database connection: " + e.getMessage());
                }
            }
        }
        
        
        //loadFromSQLDatabase();

        while (true) {
            System.out.println("1. Add a new book");
            System.out.println("2. Edit a book");
            System.out.println("3. Delete a book");
            System.out.println("4. List books");
            System.out.println("5. List by title");
            System.out.println("6. Mark book as free");
            System.out.println("7. Mark book as notfree");
            System.out.println("8. List by Author");
            System.out.println("9. List by Genre");    
            System.out.println("10. Save to file");
            System.out.println("11. Load from file");
            
            System.out.println("0. Exit");
            System.out.print("Choose an option: ");
            int option = scanner.nextInt();

            switch (option) {
                case 1:
                    addBook(scanner);
                    break;
                case 2:
                    editBook(scanner);
                    break;
                case 3:
                    deleteBook(scanner);
                    break;
                case 0:
                	saveToSQLDatabase();
                    System.exit(0);
                    break;
                case 4:
                	listBooks();
                    break;
                case 5:
                	scanner.nextLine();
                    String title = scanner.nextLine();
                    searchBook(title);
                    break;
                case 6:
                	scanner.nextLine();
                    markBook(scanner, true);
                    break;
                case 7:
                	scanner.nextLine();
                    markBook(scanner, false);
                    break;
                case 8:
                	scanner.nextLine();
                    listBooksByAuthor(scanner);
                    break;
                case 9:
                	scanner.nextLine();
                    listBooksByGenre(scanner);
                    break;
                case 10:
                	scanner.nextLine();
                    System.out.print("Enter the title of the book to save: ");
                    String selectedBookTitle = scanner.nextLine();
                    saveBookToFile(selectedBookTitle);
                    break;
                case 11:
                	scanner.nextLine();
                    loadBookFromFile();
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    
    private static void markBook(Scanner scanner, boolean isBorrowed) {
        System.out.print("enter the title of the book to mark as " + (isBorrowed ? "borrowed" : "returned") + ": ");
        String title = scanner.nextLine();

        for (int i = 0; i < bookList.size(); i++) {
            if (bookList.get(i).getTitle().equalsIgnoreCase(title)) {
                bookList.get(i).setAvailable(!isBorrowed);
                System.out.println("the book has been marked as " + (isBorrowed ? "borrowed" : "returned") + " successfully!");
                return;
            }
        }

        System.out.println("The book was not found.");
    }
    
    
    private static void addBook(Scanner scanner) {
        System.out.print("enter book title: ");
        String title = scanner.next();
        System.out.print("authors (comma separated): ");
        String[] authors = scanner.next().split(",");
        System.out.print("year of publication: ");
        int yearOfPublication = scanner.nextInt();
        System.out.print("is  book available? (true/false): ");
        boolean isAvailable = scanner.nextBoolean(); // Added class type 'boolean'

        System.out.print("novel or a textbook? (novel/textbook): ");
        String type = scanner.next();

        if (type.equalsIgnoreCase("novel")) {
            System.out.print("genre: ");
            String genre = scanner.next();
            bookList.add(new Novel(title, authors, yearOfPublication, isAvailable, genre));
        } else if (type.equalsIgnoreCase("textbook")) {
            System.out.print("grade: ");
            int grade = scanner.nextInt();
            bookList.add(new Textbook(title, authors, yearOfPublication, isAvailable, grade));
        } else {
            System.out.println("Invalid book type. Please try again.");
        }
    }
    
    private static void deleteBook(Scanner scanner) {
        System.out.print("enter book title to delete: ");
        String title = scanner.next();

        for (int i = 0; i < bookList.size(); i++) {
            if (bookList.get(i).getTitle().equals(title)) {
                bookList.remove(i);
                System.out.println("book deleted successfully!");
                return;
            }
        }

        System.out.println("book not found!");
    }

    private static void editBook(Scanner scanner) {
        System.out.print("enter book title to edit: ");
        String title = scanner.next();

        for (Book book : bookList) {
            if (book.getTitle().equals(title)) {
                System.out.print("enter new authors (comma separated): ");
                String[] authors = scanner.next().split(",");
                book.setAuthors(authors);
                System.out.print("enter new year of publication: ");
                int yearOfPublication = scanner.nextInt();
                book.setYearOfPublication(yearOfPublication);
                System.out.print("is the book available? (true/false): ");
                boolean isAvailable = scanner.nextBoolean();
                book.setAvailable(isAvailable);
                return;
            }
        }

            System.out.println("Book not found!");
    }
    
    
    private static void listBooks() {
        bookList.sort(Comparator.comparing(book -> book.getTitle().toLowerCase()));
        System.out.println("List of books:");
        for (Book book : bookList) {
            System.out.println(book.getTitle() + " by " + String.join(", ", book.getAuthors()) +
                    ", published in " + book.getYearOfPublication() +
                    ", availability status: " + (book.isAvailable() ? "available" : "borrowed"));
            if (book instanceof Novel) {
                Novel novel = (Novel) book;
                System.out.println("Genre: " + novel.getGenre());
            } else if (book instanceof Textbook) {
                Textbook textbook = (Textbook) book;
                System.out.println("Suitable for grade: " + textbook.getGrade());
            }
            System.out.println("-----------------------------------");
        }
    }
    
    private static void listBooks(List<Book> books) {
        books.sort(Comparator.comparing(book -> book.getTitle().toLowerCase()));
        System.out.println("List of books:");
        for (Book book : books) {
            System.out.println(book.getTitle() + " by " + String.join(", ", book.getAuthors()) +
                    ", published in " + book.getYearOfPublication() +
                    ", availability status: " + (book.isAvailable() ? "available" : "borrowed"));
            if (book instanceof Novel) {
                Novel novel = (Novel) book;
                System.out.println("Genre: " + novel.getGenre());
            } else if (book instanceof Textbook) {
                Textbook textbook = (Textbook) book;
                System.out.println("Suitable for grade: " + textbook.getGrade());
            }
            System.out.println("-----------------------------------");
        }
    }
    
    
    private static void searchBook(String title) {
        for (Book book : bookList) {
            if (book.getTitle().equalsIgnoreCase(title)) {
                System.out.println(book.getTitle() + " by " + String.join(", ", book.getAuthors()) +
                        ", published in " + book.getYearOfPublication() +
                        ", availability status: " + (book.isAvailable() ? "available" : "borrowed"));
                if (book instanceof Novel) {
                    Novel novel = (Novel) book;
                    System.out.println("Genre: " + novel.getGenre());
                } else if (book instanceof Textbook) {
                    Textbook textbook = (Textbook) book;
                    System.out.println("Suitable for grade: " + textbook.getGrade());
                }
                return;
            }
        }
        System.out.println("Book not found.");
    }
    
    
    /*
    private static void listBooksByAuthor(Scanner scanner) {
        System.out.print("Enter the author's name: ");
        String author = scanner.nextLine();

        List<Book> booksByAuthor = new ArrayList<>();
        for (Book book : bookList) {
            for (String bookAuthor : book.getAuthors()) {
                if (bookAuthor.equalsIgnoreCase(author)) {
                    booksByAuthor.add(book);
                    break;
                }
            }
        }

        if (booksByAuthor.isEmpty()) {
            System.out.println("No books found by this author.");
            return;
        }

        booksByAuthor.sort(Comparator.comparingInt(Book::getYearOfPublication));

        System.out.println("Books by " + author + ":");
        for (Book book : booksByAuthor) {
            System.out.println(book.getTitle() + " (" + book.getYearOfPublication() + ")");
        }
    }

    private static void listBooksByGenre(Scanner scanner) {
        System.out.print("Enter the genre: ");
        String genre = scanner.nextLine();

        List<Book> booksByGenre = new ArrayList<>();
        for (Book book : bookList) {
            if (book instanceof Novel && ((Novel) book).getGenre().equalsIgnoreCase(genre)) {
                booksByGenre.add(book);
            }
        }

        if (booksByGenre.isEmpty()) {
            System.out.println("No books found in this genre.");
            return;
        }

        System.out.println("Books in the genre " + genre + ":");
        for (Book book : booksByGenre) {
            System.out.println(book.getTitle() + " (" + book.getYearOfPublication() + ")");
        }
    }*/
    
      
    private static void listBooksByAuthor(Scanner scanner) {
        System.out.print("Enter the author's name: ");
        String author = scanner.nextLine();

        List<Book> booksByAuthor = new ArrayList<>();
        for (Book book : bookList) {
            for (String bookAuthor : book.getAuthors()) {
                if (bookAuthor.equalsIgnoreCase(author)) {
                    booksByAuthor.add(book);
                    break;
                }
            }
        }

        if (!booksByAuthor.isEmpty()) {
            System.out.println("\nList of books by " + author + ":\n");
            listBooks(booksByAuthor);
        } else {
            System.out.println("No books found by this author.");
        }
    }

    
    
    
    private static void listBooksByGenre(Scanner scanner) {
        System.out.print("Enter the genre: ");
        String genre = scanner.nextLine();

        List<Book> booksByGenre = new ArrayList<>();
        for (Book book : bookList) {
            if (book instanceof Novel && ((Novel) book).getGenre().equalsIgnoreCase(genre)) {
                booksByGenre.add(book);
            }
        }

        if (!booksByGenre.isEmpty()) {
            System.out.println("\nList of books in the genre " + genre + ":\n");
            listBooks(booksByGenre);
        } else {
            System.out.println("No books found in this genre.");
        }
    }

    
    
    private static void saveBookToFile(String selectedBookTitle) {
        Book selectedBook = null;
        for (Book book : bookList) {
            if (book.getTitle().equalsIgnoreCase(selectedBookTitle)) {
                selectedBook = book;
                break;
            }
        }

        if (selectedBook == null) {
            System.out.println("Book not found.");
            return;
        }

        try (FileWriter writer = new FileWriter("selected_book.txt")) {
            writer.write(selectedBook.getTitle() + "\n");
            writer.write(String.join(",", selectedBook.getAuthors()) + "\n");
            writer.write(selectedBook.getYearOfPublication() + "\n");
            writer.write(selectedBook.isAvailable() + "\n");

            if (selectedBook instanceof Novel) {
                Novel novel = (Novel) selectedBook;
                writer.write(novel.getGenre() + "\n");
            } else if (selectedBook instanceof Textbook) {
                Textbook textbook = (Textbook) selectedBook;
                writer.write(textbook.getGrade() + "\n");
            }
        } catch (IOException e) {
            System.out.println("Error saving book to file.");
            e.printStackTrace();
        }

        System.out.println("Book saved to file.");
    }
    
    
    
    private static void loadBookFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader("selected_book.txt"))) {
            String title = reader.readLine();
            String[] authors = reader.readLine().split(",");
            int yearOfPublication = Integer.parseInt(reader.readLine());
            boolean isAvailable = Boolean.parseBoolean(reader.readLine());
            String genreOrGrade = reader.readLine();

            Book book;
            if (genreOrGrade.matches("\\d+")) {
                int grade = Integer.parseInt(genreOrGrade);
                book = new Textbook(title, authors, yearOfPublication, isAvailable, grade);
            } else {
                book = new Novel(title, authors, yearOfPublication, isAvailable, genreOrGrade);
            }

            bookList.add(book);
            System.out.println("Book loaded from file.");
        } catch (IOException e) {
            System.err.println("Error loading book from file: " + e.getMessage());
        }
    }
    
    
    private static void saveToSQLDatabase() {
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:library.db")) {
            conn.setAutoCommit(false); // Enable transactions

            for (Book book : bookList) {
                String query = "INSERT INTO books (title, authors, year_of_publication, is_available) VALUES (?, ?, ?, ?)";
                try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                    pstmt.setString(1, book.getTitle());
                    pstmt.setString(2, String.join(", ", book.getAuthors()));
                    pstmt.setInt(3, book.getYearOfPublication());
                    pstmt.setBoolean(4, book.isAvailable());
                    pstmt.executeUpdate();
                }

                if (book instanceof Novel) {
                    Novel novel = (Novel) book;
                    query = "INSERT INTO novels (title, genre) VALUES (?, ?)";
                    try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                        pstmt.setString(1, book.getTitle());
                        pstmt.setString(2, novel.getGenre());
                        pstmt.executeUpdate();
                    }
                } else if (book instanceof Textbook) {
                    Textbook textbook = (Textbook) book;
                    query = "INSERT INTO textbooks (title, grade) VALUES (?, ?)";
                    try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                        pstmt.setString(1, book.getTitle());
                        pstmt.setInt(2, textbook.getGrade());
                        pstmt.executeUpdate();
                    }
                }
            }

            conn.commit(); // Commit the transaction
            System.out.println("Data saved to SQL database.");
        } catch (SQLException e) {
            System.err.println("Error saving data to SQL database: " + e.getMessage());
        }
    }

    private static void loadFromSQLDatabase() {
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:library.db")) {
            try (Statement stmt = conn.createStatement()) {
                try (ResultSet rs = stmt.executeQuery("SELECT * FROM books")) {
                    while (rs.next()) {
                        String title = rs.getString("title");
                        String[] authors = rs.getString("authors").split(", ");
                        int yearOfPublication = rs.getInt("year_of_publication");
                        boolean isAvailable = rs.getBoolean("is_available");

                        Book book;
                        try (ResultSet rs2 = stmt.executeQuery("SELECT * FROM novels WHERE title = '" + title + "'")) {
                            if (rs2.next()) {
                                String genre = rs2.getString("genre");
                                book = new Novel(title, authors, yearOfPublication, isAvailable, genre);
                            } else {
                                try (ResultSet rs3 = stmt.executeQuery("SELECT * FROM textbooks WHERE title = '" + title + "'")) {
                                    if (rs3.next()) {
                                        int grade = rs3.getInt("grade");
                                        book = new Textbook(title, authors, yearOfPublication, isAvailable, grade);
                                    } else {
                                        book = new Book(title, authors, yearOfPublication, isAvailable);
                                    }
                                }
                            }
                        }

                        bookList.add(book);
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Error loading data from SQL database: " + e.getMessage());
        }
    }
    
    
}