import java.util.Scanner;

public class Affine_cipher {

    // Greatest common divisor (to check that a is valid)
    static int gcd(int x, int y) {
        return y == 0 ? x : gcd(y, x % y);
    }

    // Modular inverse of a mod 26
    static int modInverse(int a) {
        a = ((a % 26) + 26) % 26;
        for (int i = 1; i < 26; i++) {
            if ((a * i) % 26 == 1) {
                return i;
            }
        }
        return -1;
    }

    // Encryption: E(x) = (a*x + b) mod 26
    static String encrypt(String text, int a, int b) {
        StringBuilder result = new StringBuilder();
        for (char ch : text.toCharArray()) {
            if (Character.isUpperCase(ch)) {
                int x = ch - 'A';
                result.append((char) (((a * x + b) % 26 + 26) % 26 + 'A'));
            } else if (Character.isLowerCase(ch)) {
                int x = ch - 'a';
                result.append((char) (((a * x + b) % 26 + 26) % 26 + 'a'));
            } else {
                result.append(ch);
            }
        }
        return result.toString();
    }

    // Decryption: D(y) = a^-1 * (y - b) mod 26
    static String decrypt(String text, int a, int b) {
        StringBuilder result = new StringBuilder();
        int aInv = modInverse(a);
        for (char ch : text.toCharArray()) {
            if (Character.isUpperCase(ch)) {
                int y = ch - 'A';
                result.append((char) (((aInv * (y - b)) % 26 + 26) % 26 + 'A'));
            } else if (Character.isLowerCase(ch)) {
                int y = ch - 'a';
                result.append((char) (((aInv * (y - b)) % 26 + 26) % 26 + 'a'));
            } else {
                result.append(ch);
            }
        }
        return result.toString();
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("===== Affine Cipher =====");

        while (true) {
            // Step 1: choose the operation first
            System.out.println("\n1. Encryption");
            System.out.println("2. Decryption");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");
            int choice = sc.nextInt();
            sc.nextLine();

            if (choice == 3) {
                System.out.println("Goodbye!");
                break;
            }
            if (choice != 1 && choice != 2) {
                System.out.println("Invalid choice, try again.");
                continue;
            }

            // Step 2: enter the keys a and b
            int a;
            while (true) {
                System.out.print("Enter key a (must be coprime with 26): ");
                a = sc.nextInt();
                if (gcd(Math.abs(a), 26) == 1) {
                    break;
                }
                System.out.println("Invalid! 'a' must be one of: "
                        + "1,3,5,7,9,11,15,17,19,21,23,25.");
            }
            System.out.print("Enter key b: ");
            int b = sc.nextInt();
            sc.nextLine();

            // Step 3: enter the text and show the result
            if (choice == 1) {
                System.out.print("Enter plaintext: ");
                String plain = sc.nextLine();
                System.out.println("Encrypted text: " + encrypt(plain, a, b));
            } else {
                System.out.print("Enter ciphertext: ");
                String cipher = sc.nextLine();
                System.out.println("Decrypted text: " + decrypt(cipher, a, b));
            }
        }
        sc.close();
    }
}