import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static List<Book> bookList = new ArrayList<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("1. Add a new book");
            System.out.println("2. Edit a book");
            System.out.println("3. Delete a book");
            System.out.println("5. List books");
            System.out.println("6. Enter title of the book to search for: ");
            System.out.println("8. Mark book as free");
            System.out.println("9. Mark book as notfree");

            System.out.println("4. Exit");
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
                case 4:
                    System.exit(0);
                    break;
                case 5:
                    listBooks();
                    break;
                case 6:
                    String title = scanner.nextLine();
                    searchBook(title);
                    break;
                case 8:
                    markBook(scanner, true);
                    break;
                case 9:
                    markBook(scanner, false);
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
    
}