/*
 * Assignment 3: Fractional Knapsack Problem (Greedy Method)
 *
 * HOW TO RUN ON UBUNTU:
 *   sudo apt update && sudo apt install default-jdk -y     (only once)
 *   javac FracKnapsack.java
 *   java FracKnapsack
 *
 * (The file name must stay FracKnapsack.java)
 *
 * Time: O(n log n)   Space: O(n)
 */

import java.util.*;

class Item {
    int id;      // original item number (kept after sorting)
    int weight;
    int profit;
    double ratio;

    Item(int id, int weight, int profit) {
        this.id = id;
        this.weight = weight;
        this.profit = profit;
        this.ratio = (double) profit / weight;
    }
}

public class FracKnapsack {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter number of items: ");
        int n = sc.nextInt();

        if (n <= 0) {
            System.out.println("Number of items must be positive.");
            return;
        }

        Item[] items = new Item[n];

        for (int i = 0; i < n; i++) {
            System.out.print("Enter weight of item " + (i + 1) + ": ");
            int weight = sc.nextInt();
            System.out.print("Enter profit of item " + (i + 1) + ": ");
            int profit = sc.nextInt();

            if (weight <= 0 || profit < 0) {
                System.out.println("Weight must be positive and profit cannot be negative.");
                return;
            }
            items[i] = new Item(i + 1, weight, profit);
        }

        System.out.print("Enter capacity of knapsack: ");
        int capacity = sc.nextInt();

        if (capacity <= 0) {
            System.out.println("Capacity must be positive.");
            return;
        }

        // Sort items by profit/weight ratio in descending order
        Arrays.sort(items, (a, b) -> Double.compare(b.ratio, a.ratio));

        double totalProfit = 0.0;
        int remainingCapacity = capacity;

        System.out.println("\nItems selected:");

        for (int i = 0; i < n; i++) {
            if (remainingCapacity == 0)
                break;

            if (items[i].weight <= remainingCapacity) {
                // Take the complete item
                remainingCapacity -= items[i].weight;
                totalProfit += items[i].profit;
                System.out.println("Item " + items[i].id + ": 100% taken");
            } else {
                // Take the fractional part
                double fraction = (double) remainingCapacity / items[i].weight;
                totalProfit += items[i].profit * fraction;
                System.out.printf("Item %d: %.2f%% taken%n", items[i].id, fraction * 100);
                remainingCapacity = 0;
            }
        }

        System.out.printf("%nMaximum Profit = %.2f%n", totalProfit);
        sc.close();
    }
}

/*
 * ===================== SAMPLE INPUTS AND OUTPUTS =====================
 *
 * Run 1: standard example (n = 3, capacity = 50)
 *   Enter number of items: 3
 *   Enter weight of item 1: 10
 *   Enter profit of item 1: 60
 *   Enter weight of item 2: 20
 *   Enter profit of item 2: 100
 *   Enter weight of item 3: 30
 *   Enter profit of item 3: 120
 *   Enter capacity of knapsack: 50
 *
 *   Items selected:
 *   Item 1: 100% taken
 *   Item 2: 100% taken
 *   Item 3: 66.67% taken
 *
 *   Maximum Profit = 240.00
 *
 * Run 2: items entered in a different order (original item numbers are kept)
 *   Enter number of items: 3
 *   Enter weight of item 1: 30
 *   Enter profit of item 1: 120
 *   Enter weight of item 2: 10
 *   Enter profit of item 2: 60
 *   Enter weight of item 3: 20
 *   Enter profit of item 3: 100
 *   Enter capacity of knapsack: 50
 *
 *   Items selected:
 *   Item 2: 100% taken
 *   Item 3: 100% taken
 *   Item 1: 66.67% taken
 *
 *   Maximum Profit = 240.00
 *
 * Run 3: five items, capacity = 60
 *   Enter number of items: 5
 *   Enter weight of item 1: 5
 *   Enter profit of item 1: 30
 *   Enter weight of item 2: 10
 *   Enter profit of item 2: 40
 *   Enter weight of item 3: 15
 *   Enter profit of item 3: 45
 *   Enter weight of item 4: 22
 *   Enter profit of item 4: 77
 *   Enter weight of item 5: 25
 *   Enter profit of item 5: 90
 *   Enter capacity of knapsack: 60
 *
 *   Items selected:
 *   Item 1: 100% taken
 *   Item 2: 100% taken
 *   Item 5: 100% taken
 *   Item 4: 90.91% taken
 *
 *   Maximum Profit = 230.00
 *
 * Run 4: all items fit (capacity = 100)
 *   Enter number of items: 3
 *   Enter weight of item 1: 10
 *   Enter profit of item 1: 60
 *   Enter weight of item 2: 20
 *   Enter profit of item 2: 100
 *   Enter weight of item 3: 30
 *   Enter profit of item 3: 120
 *   Enter capacity of knapsack: 100
 *
 *   Items selected:
 *   Item 1: 100% taken
 *   Item 2: 100% taken
 *   Item 3: 100% taken
 *
 *   Maximum Profit = 280.00
 *
 * Run 5: invalid input (weight = 0)
 *   Enter number of items: 1
 *   Enter weight of item 1: 0
 *   Enter profit of item 1: 10
 *   Weight must be positive and profit cannot be negative.
 */

/*
 * ======================================================================
 *                  THEORY (for viva / written part)
 * ======================================================================
 *
 * Assignment 3: Fractional Knapsack Problem
 * Solved using the Greedy Method, with Complexity Analysis
 *
 * Problem Statement
 * Write a program to solve a fractional knapsack problem using a greedy method. Given n items, each
 * with a weight and a profit, and a knapsack of limited capacity, choose items (or fractions of items) so
 * that the total profit is maximum and the total weight does not exceed the capacity.
 *
 * Algorithm
 * 1. For each item, calculate ratio = profit / weight.
 * 2. Sort the items by ratio in descending order (greedy choice).
 * 3. Go through the sorted items. If the whole item fits in the remaining capacity, take it fully and add its
 * profit.
 * 4. If it does not fit, take only the fraction (remaining capacity / weight) of it, add that part of the profit,
 * and stop.
 * 5. The total profit collected is the maximum profit.
 *
 * Complexity (n = number of items)
 * Time    O(n log n)   sorting the items by ratio dominates; the selection loop is O(n)
 * Space   O(n)         the n items are stored (id, weight, profit, ratio)
 * Detailed analysis is given in the Complexity Analysis section.
 *
 *
 * Working of Run 1
 * Item   Weight   Profit   Ratio   Remaining capacity   Taken    Profit added
 * 1      10       60       6.00    50 -> 40             100%     60.00
 * 2      20       100      5.00    40 -> 20             100%     100.00
 * 3      30       120      4.00    20 -> 0              66.67%   80.00
 *
 * Items 1 and 2 are taken completely (60 + 100 = 160). Only 20 of the 30 units of item 3 fit, so 2/3 of it
 * is taken: 120 x 20/30 = 80. Maximum profit = 160 + 80 = 240.00.
 *
 * Complexity Analysis
 * Time Complexity
 * Calculating ratios takes O(n). Sorting takes O(n log n). The selection loop visits each item at most
 * once, so it takes O(n). The sort dominates, so the total is O(n log n).
 *
 * Space Complexity
 * The program stores the n items (id, weight, profit, ratio), so the space is O(n). Only a few extra
 * variables are used.
 *
 * Why the greedy method is optimal
 * Because items can be divided, every unit of capacity should hold the item with the highest profit per
 * unit weight that is still available. Replacing any part of that choice with a lower-ratio item can only
 * reduce (or keep equal) the total profit. So the greedy choice always leads to the optimal answer.
 *
 * Fractional vs 0/1 Knapsack
 *                         Fractional knapsack   0/1 knapsack
 * Can an item be split?   Yes                   No (take it or leave it)
 * Method                  Greedy                Dynamic programming
 * Time                    O(n log n)            O(n x W)
 * Is greedy optimal?      Yes                   No
 *
 * Viva Questions and Answers
 * Q1. What is the fractional knapsack problem?
 * Given items with weights and profits and a knapsack of fixed capacity, choose items, or parts of
 * items, to get the maximum total profit without exceeding the capacity. Items can be broken into
 * fractions.
 *
 * Q2. What is the greedy method?
 * An approach that builds the solution step by step, always making the choice that looks best at that
 * moment, and never going back to change it.
 *
 * Q3. What is the greedy choice in this problem?
 * Always pick the item with the highest profit-to-weight ratio that is still available.
 *
 * Q4. Why do we sort by profit/weight ratio and not by profit or by weight?
 * Profit alone ignores how much capacity the item uses, and weight alone ignores the profit. The
 * ratio tells how much profit we get for each unit of capacity, which is what we want to maximise.
 *
 * Q5. What is the difference between fractional and 0/1 knapsack?
 * In fractional knapsack an item can be split, so greedy gives the optimal answer. In 0/1 knapsack
 * an item must be taken completely or not at all, and it needs dynamic programming.
 *
 * Q6. Does the greedy method work for 0/1 knapsack?
 * No. Example: capacity 50 with items (10, 60), (20, 100), (30, 120). Greedy takes items 1 and 2 for
 * a profit of 160, but the best 0/1 answer is items 2 and 3 for 220.
 *
 * Q7. What is the time complexity of your program?
 * O(n log n), because sorting dominates. The selection loop is only O(n).
 *
 * Q8. What is the space complexity?
 * O(n) to store the items.
 *
 * Q9. How many items can be taken partially?
 * At most one, the last item that does not fit completely. After that the knapsack is full.
 *
 * Q10. What happens if the total weight of all items is less than the capacity?
 * All items are taken completely and the answer is the sum of all profits (for example 280 in Run 4).
 *
 * Q11. What if two items have the same ratio?
 * They can be taken in either order. The maximum profit is the same.
 *
 * Q12. Why does the Item class have an id field?
 * After sorting, the original position of each item is lost. The id remembers the item number the user
 * entered, so the output shows the correct item.
 *
 * Q13. Why must the weight be positive?
 * The ratio is profit / weight. A weight of zero gives a division by zero (infinite ratio), so the program
 * rejects it.
 *
 * Q14. Explain the line Arrays.sort(items, (a, b) -> Double.compare(b.ratio, a.ratio));
 * It sorts the array using a comparator. For two items a and b it compares their ratios with b first,
 * which puts the item with the higher ratio earlier, so the order is descending.
 *
 * Q15. Why is the greedy method optimal here?
 * Because items can be split, putting capacity on the highest ratio item first can never be worse.
 * Replacing any part of it with a lower ratio item cannot increase the profit.
 *
 * Q16. Can the time complexity be reduced below O(n log n)?
 * Yes, in theory. Using a selection algorithm such as median of medians instead of a full sort gives
 * O(n), but sorting is simpler and is what is expected in practice.
 *
 * Q17. What are real-life applications?
 * Loading cargo or filling a truck with goods that can be divided (grain, liquids, sand), allocating a
 * limited budget or time across tasks, and resource allocation where partial use is allowed.
 *
 * Conclusion
 * The greedy method solves the fractional knapsack problem optimally by sorting items by profit per
 * unit weight and filling the knapsack in that order, taking a fraction of the last item if needed. The
 * program runs in O(n log n) time and O(n) space.
 */
