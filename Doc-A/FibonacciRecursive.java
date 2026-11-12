/*
 * Assignment 1 (Program 2): Fibonacci Numbers - Recursive
 *
 * HOW TO RUN ON UBUNTU:
 *   sudo apt update && sudo apt install default-jdk -y     (only once)
 *   javac FibonacciRecursive.java
 *   java FibonacciRecursive
 *
 * (The file name must stay FibonacciRecursive.java)
 *
 * Time: O(2^n)   Space: O(n) (call stack)
 */

import java.util.Scanner;

public class FibonacciRecursive {
    static long stepCount = 0;

    static long fibonacci(int n) {
        stepCount++; // count every function call
        if (n == 0) return 0;
        if (n == 1) return 1;
        return fibonacci(n - 1) + fibonacci(n - 2);
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter the value of n: ");
        int n = sc.nextInt();

        if (n < 0) {
            System.out.println("Please enter a non-negative number.");
            return;
        }

        long result = fibonacci(n);
        System.out.println("Fibonacci number = " + result);
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
 *   Step Count = 15
 *
 * Run 3 (n = 10)
 *   Enter the value of n: 10
 *   Fibonacci number = 55
 *   Step Count = 177
 *
 * Run 4 (n = 20)
 *   Enter the value of n: 20
 *   Fibonacci number = 6765
 *   Step Count = 21891
 *
 * Run 5 (n = -3, invalid input)
 *   Enter the value of n: -3
 *   Please enter a non-negative number.
 */
