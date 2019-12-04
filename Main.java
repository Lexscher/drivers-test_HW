public class Main {
    public static void main(String[] args) {
        // Correct Answers:
        // 'B', 'A', 'A', 'C', 'D', 'A', 'B', 'A', 'A', 'C', 'C', 'D', 'A', 'B', 'A',
        // 'C','D', 'A', 'D', 'B'
        DriverExam sam = new DriverExam(true);
        sam.gradeExam();
        sam.passedExam();
    }

    public void puts(String str) {
        System.out.println(str);
    }
}