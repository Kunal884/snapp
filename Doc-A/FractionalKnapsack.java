/*
 * Assignment 3: Fractional Knapsack Problem (Greedy Method)
 *
 * HOW TO RUN ON UBUNTU:
 *   sudo apt update && sudo apt install default-jdk -y     (only once)
 *   javac FractionalKnapsack.java
 *   java FractionalKnapsack
 *
 * (The file name must stay FractionalKnapsack.java)
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

public class FractionalKnapsack {
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
