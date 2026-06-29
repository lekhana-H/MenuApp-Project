import java.util.Random;
import java.util.Scanner;

public class PasswordGenerator{
    // ── Character pools ──────────────────────────────────────────────────────
    static final String UPPERCASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    static final String LOWERCASE = "abcdefghijklmnopqrstuvwxyz";
    static final String DIGITS    = "0123456789";
    static final String SYMBOLS   = "!@#$%^&*()-_=+[]{}|;:,.<>?";

    // All characters combined into one big pool
    static final String ALL_CHARS = UPPERCASE + LOWERCASE + DIGITS + SYMBOLS;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random   = new Random();

        System.out.println("╔══════════════════════════════════╗");
        System.out.println("║    Random Password Generator     ║");
        System.out.println("╚══════════════════════════════════╝");

        boolean keepGoing = true;

        while (keepGoing) {
            // ── Step 1: Ask for password length ──────────────────────────────
            int length = 0;
            while (length < 4) {
                System.out.print("\nEnter password length (min 4, max 64): ");
                String input = scanner.nextLine().trim();

                // Input validation — make sure it's actually a number
                try {
                    length = Integer.parseInt(input);
                    if (length < 4 || length > 64) {
                        System.out.println("Please enter a number between 4 and 64.");
                        length = 0; // reset so the loop continues
                    }
                } catch (NumberFormatException e) {
                    System.out.println("That doesn't look like a number. Try again!");
                }
            }

            // ── Step 2: Ask what character types to include ──────────────────
            System.out.println("\nInclude in password? (y/n)");

            boolean useUpper   = askYesNo(scanner, "  Uppercase letters (A-Z)");
            boolean useLower   = askYesNo(scanner, "  Lowercase letters (a-z)");
            boolean useDigits  = askYesNo(scanner, "  Numbers (0-9)       ");
            boolean useSymbols = askYesNo(scanner, "  Symbols (!@#$...)   ");

            // If the user said 'no' to everything, just use all characters
            if (!useUpper && !useLower && !useDigits && !useSymbols) {
                System.out.println("No types selected — using all character types.");
                useUpper = useLower = useDigits = useSymbols = true;
            }

            // ── Step 3: Build the character pool based on choices ────────────
            StringBuilder pool = new StringBuilder();
            if (useUpper)   pool.append(UPPERCASE);
            if (useLower)   pool.append(LOWERCASE);
            if (useDigits)  pool.append(DIGITS);
            if (useSymbols) pool.append(SYMBOLS);

            String charPool = pool.toString();

            // ── Step 4: Generate the password ────────────────────────────────
            //
            // TRICK: To guarantee at least one character from each selected
            // type, we pick one character from each required group first,
            // then fill the rest randomly from the full pool.
            // Finally we SHUFFLE so those guaranteed chars aren't always first.

            StringBuilder password = new StringBuilder();

            // Add at least one from each selected group
            if (useUpper)   password.append(randomChar(UPPERCASE, random));
            if (useLower)   password.append(randomChar(LOWERCASE, random));
            if (useDigits)  password.append(randomChar(DIGITS,    random));
            if (useSymbols) password.append(randomChar(SYMBOLS,   random));

            // Fill remaining slots from the full pool
            while (password.length() < length) {
                password.append(randomChar(charPool, random));
            }

            // Shuffle the password so guaranteed chars are in random positions
            shuffle(password, random);

            // ── Step 5: Display the result ───────────────────────────────────
            System.out.println("\n┌─────────────────────────────────────┐");
            System.out.println("│  Your Password:                     │");
            System.out.println("│                                     │");
            System.out.printf( "│  %-35s  │%n", password.toString());
            System.out.println("│                                     │");
            System.out.println("└─────────────────────────────────────┘");

            // Show password strength
            System.out.println("  Strength : " + getStrength(length, useUpper, useLower, useDigits, useSymbols));
            System.out.println("  Length   : " + length + " characters");

            // ── Step 6: Ask if they want another password ────────────────────
            System.out.print("\nGenerate another? (y/n): ");
            String again = scanner.nextLine().trim().toLowerCase();
            keepGoing = again.equals("y") || again.equals("yes");
        }

        System.out.println("\nStay safe online! Goodbye.");
        scanner.close();
    }

    static char randomChar(String source, Random random) {
        int index = random.nextInt(source.length()); // random index 0 to length-1
        return source.charAt(index);
    }

   
    static void shuffle(StringBuilder sb, Random random) {
        for (int i = sb.length() - 1; i > 0; i--) {
            int j = random.nextInt(i + 1);      // pick a random earlier index
            char temp  = sb.charAt(i);
            sb.setCharAt(i, sb.charAt(j));      // swap
            sb.setCharAt(j, temp);
        }
    }

    
    static boolean askYesNo(Scanner scanner, String question) {
        System.out.print(question + " (y/n): ");
        String answer = scanner.nextLine().trim().toLowerCase();
        return answer.equals("y") || answer.equals("yes");
    }

    
    static String getStrength(int length, boolean upper, boolean lower,
                               boolean digits, boolean symbols) {
        // Count how many character types are being used
        int variety = 0;
        if (upper)   variety++;
        if (lower)   variety++;
        if (digits)  variety++;
        if (symbols) variety++;

        if (length >= 16 && variety == 4) return "🟢 Very Strong";
        if (length >= 12 && variety >= 3) return "🟡 Strong";
        if (length >= 8  && variety >= 2) return "🟠 Medium";
        return                                   "🔴 Weak — try a longer password";
    }
}

