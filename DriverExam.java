import java.util.*;
import javax.swing.*;

public class DriverExam {
    // List of correct answers
    private static char answers[] = { 'B', 'A', 'A', 'C', 'D', 'A', 'B', 'A', 'A', 'C', 'C', 'D', 'A', 'B', 'A', 'C',
            'D', 'A', 'D', 'B' };

    // If CLI is false, then we'll be using Swing for the user experience
    private static boolean cliMode, graded;

    // The incorrect answers will be stored here as the proctor enters a student's
    private static Hashtable<Integer, Character> incorrectAnswers = new Hashtable<Integer, Character>();

    // this final grade will be set
    private static double finalGrade = 0.00;

    public DriverExam(final boolean initialCLIMode) {
        cliMode = initialCLIMode;
    }

    // Utilities
    public void puts(String str) {
        System.out.println(str);
    }

    // getters
    public char[] getAnswers() {
        return answers;
    }

    public Hashtable<Integer, Character> getIncorrectAnswers() {
        return incorrectAnswers;
    }

    // setters
    public void addIncorrectAnswer(int num, char letter) {
        incorrectAnswers.put(num, letter);
    }

    // Logic/Helpers
    // This will tell us if the student has passed or failed the drivers exam
    public boolean passedExam() {
        // A student must answer at least 15 questions correctly to pass the exam.

        if (getIncorrectAnswers().size() > 5) {
            puts("Sorry, you didn't pass because you scored a " + getFinalGrade() + ", with "
                    + (20 - getIncorrectAnswers().size()) + " out of 20 questions answered correctly.");
            puts("Please review these questions: " + getIncorrectAnswers());
            return false;
        }

        puts("Congratulations!! You pass with a " + getFinalGrade() + ". You answered "
                + (20 - getIncorrectAnswers().size()) + " out of 20 questions correctly!");
        return true;
    }

    // This will return the final grade
    public double getFinalGrade() {
        // If the exam hasn't been graded yet
        if (!graded) {
            // Let the user know they must grade the exam first
            JOptionPane.showMessageDialog(null, "You must grade the exam in order to get the final grade!",
                    "Warning ⚠️", JOptionPane.WARNING_MESSAGE);
            // Ask them if they'd like to grade it now.
            int proctorsAnswer = JOptionPane.showConfirmDialog(null, "Would you like to grade this student now?",
                    "Please confirm", JOptionPane.YES_NO_OPTION);
            if (proctorsAnswer == JOptionPane.YES_OPTION) {
                // Grade the exam
                this.gradeExam();
            } else {
                // Exit without grading the exam
                JOptionPane.showMessageDialog(null, "Okay, that's fine. Hope to see you soon!!", "Bye Bye 👋",
                        JOptionPane.PLAIN_MESSAGE);
                return -1.0;
            }
        }

        this.setFinalGrade();
        return finalGrade;
    }

    // This will create the student's final grade based on how many incorrect
    // answers they have
    public void setFinalGrade() {
        // set grade to 100.00
        double grade = 100.0;

        // calculate the final grade based on the size of the incorrect answers
        // hashtable
        // Store the number of wrong answers in a variable
        double wrongAnswerCount = (double) incorrectAnswers.size();
        // calculate the student's grade
        grade = grade - (wrongAnswerCount * grade) / 20.0;

        // set the grade
        finalGrade = grade;
    }

    public void gradeExam() {
        if (cliMode) {
            gradeExamViaCLI();
        } else {
            gradeExamViaSwing();
        }
    }

    private void gradeExamViaCLI() {
        System.out.println("Please, enter the student's grades.");
        // This code will execute 20 times (20 answers to grade)
        for (int i = 0; i < answers.length; i++) {
            // This method handles to functionality to grade a single question.
            puts("For loop iteration: " + i + "");
            enterSingleStudentAnswer(i);
        }
        // Set graded to true
        graded = true;
    }

    private void gradeExamViaSwing() {

    }

    private void enterSingleStudentAnswer(int questionNumber) {
        // Set valid to false
        boolean valid = false;
        // While valid is false
        outerWhileLoop: while (valid != true) {
            Scanner sc = new Scanner(System.in);
            String strAnswer;
            char answer;
            // ask the user to enter a grade

            System.out.println("Please enter the student's answer for question " + (questionNumber + 1) + ": ");
            while (sc.hasNextLine()) {
                strAnswer = sc.nextLine();
                answer = strAnswer.charAt(0);
                System.out.println("You entered " + answer);
                // If the answer isn't (or can't be converted to) A, B, C or D, let the user
                // know that answer was invalid
                char upcaseAnswer = Character.toUpperCase(answer);
                if (upcaseAnswer == 'A' || upcaseAnswer == 'B' || upcaseAnswer == 'C' || upcaseAnswer == 'D') {
                    // If the answer is wrong
                    if (upcaseAnswer != answers[questionNumber]) {
                        // Keep track of the incorrect answer, this will be a hash.
                        puts(upcaseAnswer + " is wrong, the correct answer is " + getAnswers()[questionNumber] + ".");
                        puts("incorrect answers hashtable: " + getIncorrectAnswers());
                        incorrectAnswers.put(questionNumber + 1, upcaseAnswer);
                    }
                    puts("Answers at index " + questionNumber + ": " + getAnswers()[questionNumber]);
                    puts("Time for the next question\n");
                    // Set valid to true so we exit the while loop.
                    valid = true;
                    break outerWhileLoop;
                } else {
                    System.out.println(
                            "Sorry, are you sure you entered the correct answer?\nThe test only accepts \"A\", \"B\", \"C\", or \"D\". You entered"
                                    + answer + ".\nPlease, try to do it again.");
                }
                sc.hasNext();
            }

            // Close the scanner
            sc.close();
        }
    }
}