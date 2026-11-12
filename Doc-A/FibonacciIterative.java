/*
 * Assignment 1 (Program 1): Fibonacci Numbers - Non-Recursive (Iterative)
 *
 * HOW TO RUN ON UBUNTU:
 *   sudo apt update && sudo apt install default-jdk -y     (only once)
 *   javac FibonacciIterative.java
 *   java FibonacciIterative
 *
 * (The file name must stay FibonacciIterative.java)
 *
 * Time: O(n)   Space: O(1)
 */

import java.util.Scanner;

public class FibonacciIterative {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter the value of n: ");
        int n = sc.nextInt();

        if (n < 0) {
            System.out.println("Please enter a non-negative number.");
            return;
        }

        long a = 0, b = 1, c;
        long stepCount = 0;

        if (n == 0) {
            System.out.println("Fibonacci number = 0");
            stepCount = 1;
        } else if (n == 1) {
            System.out.println("Fibonacci number = 1");
            stepCount = 1;
        } else {
            for (int i = 2; i <= n; i++) {
                c = a + b; stepCount++; // addition
                a = b;     stepCount++; // assignment
                b = c;     stepCount++; // assignment
            }
            System.out.println("Fibonacci number = " + b);
        }

        System.out.println("Step Count = " + stepCount);
        sc.close();
    }
}

/*
 * ===================== SAMPLE INPUTS AND OUTPUTS =====================
 *
 * Run 1 (n = 0, base case)
 *   Enter the value of n: 0
 *   Fibonacci number = 0
 *   Step Count = 1
 *
 * Run 2 (n = 5)
 *   Enter the value of n: 5
 *   Fibonacci number = 5
 *   Step Count = 12
 *
 * Run 3 (n = 10)
 *   Enter the value of n: 10
 *   Fibonacci number = 55
 *   Step Count = 27
 *
 * Run 4 (n = 20)
 *   Enter the value of n: 20
 *   Fibonacci number = 6765
 *   Step Count = 57
 *
 * Run 5 (n = -3, invalid input)
 *   Enter the value of n: -3
 *   Please enter a non-negative number.
 */
