/*
 * Assignment 2: Huffman Encoding (Greedy Strategy)
 *
 * HOW TO RUN ON UBUNTU:
 *   sudo apt update && sudo apt install default-jdk -y     (only once)
 *   javac HuffmanEncoding.java
 *   java HuffmanEncoding
 *
 * (The file name must stay HuffmanEncoding.java)
 *
 * Time: O(n log n) for n distinct characters   Space: O(n)
 */

import java.util.*;

class Node {
    char ch;
    int frequency;
    Node left, right;

    Node(char ch, int frequency) {
        this.ch = ch;
        this.frequency = frequency;
    }

    Node(int frequency, Node left, Node right) {
        this.frequency = frequency;
        this.left = left;
        this.right = right;
    }
}

public class HuffmanEncoding {

    // Generate Huffman codes
    static void generateCodes(Node root, String code, Map<Character, String> huffmanCode) {
        if (root == null)
            return;

        // Leaf node
        if (root.left == null && root.right == null) {
            // If the text has only one distinct character, its code would be empty, so use "0"
            huffmanCode.put(root.ch, code.isEmpty() ? "0" : code);
            return;
        }

        generateCodes(root.left, code + "0", huffmanCode);
        generateCodes(root.right, code + "1", huffmanCode);
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter text: ");
        String text = sc.nextLine();

        if (text.isEmpty()) {
            System.out.println("Text cannot be empty.");
            return;
        }

        // Count frequency of each character (TreeMap keeps the table in sorted order)
        Map<Character, Integer> frequency = new TreeMap<>();
        for (char ch : text.toCharArray()) {
            frequency.put(ch, frequency.getOrDefault(ch, 0) + 1);
        }

        // Min Heap based on frequency
        PriorityQueue<Node> pq = new PriorityQueue<>(
            Comparator.comparingInt(node -> node.frequency)
        );

        // Insert all characters into priority queue
        for (Map.Entry<Character, Integer> entry : frequency.entrySet()) {
            pq.add(new Node(entry.getKey(), entry.getValue()));
        }

        // Build Huffman Tree
        while (pq.size() > 1) {
            // Remove two nodes with smallest frequency
            Node left = pq.poll();
            Node right = pq.poll();

            // Create new node
            Node newNode = new Node(
                left.frequency + right.frequency,
                left,
                right
            );
            pq.add(newNode);
        }

        Node root = pq.poll();

        // Generate Huffman codes
        Map<Character, String> huffmanCode = new HashMap<>();
        generateCodes(root, "", huffmanCode);

        // Display frequencies and codes
        System.out.println("\nCharacter Frequency Huffman Code");
        for (Map.Entry<Character, Integer> entry : frequency.entrySet()) {
            String name = (entry.getKey() == ' ') ? "space" : String.valueOf(entry.getKey());
            System.out.printf("%-10s %-10d %s%n", name, entry.getValue(), huffmanCode.get(entry.getKey()));
        }

        // Encode the input text
        StringBuilder encodedText = new StringBuilder();
        for (char ch : text.toCharArray()) {
            encodedText.append(huffmanCode.get(ch));
        }

        System.out.println("\nOriginal Text: " + text);
        System.out.println("Encoded Text: " + encodedText);
        sc.close();
    }
}

/*
 * ===================== SAMPLE INPUTS AND OUTPUTS =====================
 *
 * Run 1: abracadabra
 *   Enter text: abracadabra
 *
 *   Character Frequency Huffman Code
 *   a 5 0
 *   b 2 10
 *   c 1 1110
 *   d 1 1111
 *   r 2 110
 *
 *   Original Text: abracadabra
 *   Encoded Text: 01011001110011110101100
 *
 * Run 2: aabbbcccc
 *   Enter text: aabbbcccc
 *
 *   Character Frequency Huffman Code
 *   a 2 10
 *   b 3 11
 *   c 4 0
 *
 *   Original Text: aabbbcccc
 *   Encoded Text: 10101111110000
 *
 * Run 3: text with a space
 *   Enter text: hello world
 *
 *   Character Frequency Huffman Code
 *   space 1 1100
 *   d 1 1111
 *   e 1 011
 *   h 1 010
 *   l 3 10
 *   o 2 00
 *   r 1 1110
 *   w 1 1101
 *
 *   Original Text: hello world
 *   Encoded Text: 01001110100011001101001110101111
 *
 * Run 4: only one distinct character
 *   Enter text: aaaa
 *
 *   Character Frequency Huffman Code
 *   a 4 0
 *
 *   Original Text: aaaa
 *   Encoded Text: 0000
 *
 * Run 5: empty input
 *   Enter text:
 *   Text cannot be empty.
 *
 * (Actual spacing of the table columns on screen may differ slightly,
 *  because the program uses printf with fixed-width columns.)
 */
