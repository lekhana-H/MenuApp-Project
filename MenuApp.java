import java.util.Scanner;
public class MenuApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int choice;
        double[] numbers = new double[5];

        // The do-while loop ensures the menu runs at least once
        do {
            System.out.println("\n--- Arithmetic Menu ---");
            System.out.println("1. Enter/Reset 5 Numbers");
            System.out.println("2. Calculate Sum");
            System.out.println("3. Calculate Average");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");
            
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.println("Enter 5 numbers:");
                    for (int i = 0; i < 5; i++) {
                        System.out.print("Number " + (i + 1) + ": ");
                        numbers[i] = scanner.nextDouble();
                    }
                    break;

                case 2:
                    double sum = 0;
                    for (double num : numbers) {
                        sum += num;
                    }
                    System.out.println("The total sum is: " + sum);
                    break;

                case 3:
                    double total = 0;
                    for (double num : numbers) {
                        total += num;
                    }
                    System.out.println("The average is: " + (total / 5));
                    break;

                case 4:
                    System.out.println("Exiting... Goodbye!");
                    System.out.println("Have A Nice Day!!");
                    break;

                default:
                    System.out.println("Invalid choice! Please try again.");
            }
        } while (choice != 4);

        scanner.close();
    }
}
