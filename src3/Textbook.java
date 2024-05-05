public class Textbook extends Book {
    private int grade;

    public Textbook(String title, String[] authors, int yearOfPublication, boolean isAvailable, int grade) {
        super(title, authors, yearOfPublication, isAvailable);
        this.grade = grade;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }
}