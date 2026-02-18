import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class GradeCalculator {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<Double> grades = new ArrayList<>();

        System.out.println("=========================================");
        System.out.println(" Welcome To The Student Grade Calculator");
        System.out.println("=========================================");
        System.out.print("Enter subject/class name (or press Enter to skip): ");
        String subject = scanner.nextLine().trim();
        System.out.println();

        System.out.println("Enter your grades one at a time.");
        System.out.println("Type 'done' when finished.\n");

        while (true) {
            System.out.print("Enter grade (or 'done'): ");
            String input = scanner.next().trim();

            if (input.equalsIgnoreCase("done")) {
                if (grades.isEmpty()) {
                    System.out.println("  No grades entered. Please enter at least one.");
                    continue;
                }
                break;
            }

            try {
                double grade = Double.parseDouble(input);
                if (grade < 0 || grade > 100) {
                    System.out.println("  Grade must be between 0 and 100.");
                } else {
                    grades.add(grade);
                    System.out.println("  Added: " + grade);
                }
            } catch (NumberFormatException e) {
                System.out.println("  Invalid input. Enter a number or 'done'.");
            }
        }

        double average = calculateAverage(grades);
        double highest = Collections.max(grades);
        double lowest  = Collections.min(grades);
        String letter  = getLetterGrade(average);

        System.out.println("\n========================================");
        System.out.println("              Results");
        System.out.println("========================================");
        if (!subject.isEmpty()) {
            System.out.printf("  Subject        : %s%n", subject);
        }
        System.out.printf("  Grades entered : %d%n", grades.size());
        System.out.printf("  Average        : %.2f%n", average);
        System.out.printf("  Letter grade   : %s%n", letter);
        System.out.printf("  Highest score  : %.2f%n", highest);
        System.out.printf("  Lowest score   : %.2f%n", lowest);
        System.out.println("========================================");

        scanner.close();
    }

    static double calculateAverage(ArrayList<Double> grades) {
        double sum = 0;
        for (double g : grades) {
            sum += g;
        }
        return sum / grades.size();
    }

    static String getLetterGrade(double average) {
        if (average >= 90) return "A";
        else if (average >= 80) return "B";
        else if (average >= 70) return "C";
        else if (average >= 60) return "D";
        else return "F";
    }
}
