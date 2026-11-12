/*
 * Assignment 2: Huffman Encoding (Greedy Strategy)
 *
 * HOW TO RUN ON UBUNTU:
 *   sudo apt update && sudo apt install default-jdk -y     (only once)
 *   javac HuffmanCoding.java
 *   java HuffmanCoding
 *
 * (The file name must stay HuffmanCoding.java)
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

public class HuffmanCoding {

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

/*
 * ======================================================================
 *                  THEORY (for viva / written part)
 * ======================================================================
 *
 * Assignment 2: Huffman Encoding
 * Implemented using a Greedy Strategy, with Complexity Analysis
 *
 * Problem Statement
 * Write a program to implement Huffman Encoding using a greedy strategy.
 *
 * Idea
 * Huffman encoding is a lossless compression method. Instead of giving every character the same
 * number of bits (for example 8), it gives short codes to frequent characters and long codes to rare
 * characters. The codes are built from a binary tree, and no code is the beginning of another code
 * (prefix property), so the encoded text can be decoded without any separators.
 *
 * Algorithm
 * 1. Read the input text.
 * 2. Count how many times each character appears (its frequency).
 * 3. Make a node for each character and put all the nodes in a min-heap (smallest frequency first).
 * 4. Repeat until only one node is left in the heap:
 * (a) Remove the two nodes with the smallest frequencies.
 * (b) Join them under a new node whose frequency is their sum.
 * (c) Put the new node back into the heap.
 * 5. The last node is the root of the Huffman tree.
 * 6. Mark every left edge 0 and every right edge 1. The path from the root to a character is its Huffman
 * code.
 * 7. Replace each character of the text by its code to get the encoded text.
 *
 * Complexity (n = number of distinct characters)
 * Time    O(n log n)   n - 1 merges, each heap operation costs O(log n)
 * Space   O(n)         heap, tree (2n - 1 nodes) and code table
 * Detailed analysis is given in the Complexity Analysis section.
 *
 *
 * Summary
 * Text          Characters   Original bits (8 per char)   Encoded bits   Space saved
 * abracadabra   11           88                           23             73.86%
 * aabbbcccc     9            72                           14             80.56%
 * hello world   11           88                           32             63.64%
 * aaaa          4            32                           4              87.50%
 *
 * Working of Run 1 (abracadabra)
 * Step 1: frequencies a = 5, b = 2, r = 2, c = 1, d = 1 (11 characters)
 * Step 2: build the tree by merging the two smallest nodes each time
 * Step   Nodes in heap before merge     Two smallest removed   New node
 * 1      c(1), d(1), b(2), r(2), a(5)   c(1) and d(1)          (2) [c, d]
 * 2      b(2), r(2), (2), a(5)          r(2) and (2)           (4) [r, (2)]
 * 3      b(2), (4), a(5)                b(2) and (4)           (6) [b, (4)]
 * 4      a(5), (6)                      a(5) and (6)           (11) [a, (6)] = root
 *
 * In step 2 three nodes have frequency 2, so any two of them could be chosen. Every choice gives a
 * different tree but the same total encoded length. The merges above are the ones this program made.
 *
 * Step 3: the Huffman tree (left edge = 0, right edge = 1)
 *         (11)
 *       0/    \1
 *     a(5)    (6)
 *           0/   \1
 *         b(2)   (4)
 *              0/   \1
 *            r(2)   (2)
 *                 0/   \1
 *               c(1)   d(1)
 *
 * Step 4: codes
 * Character   Frequency   Code   Code length   Frequency x length
 * a           5           0      1             5
 * b           2           10     2             4
 * r           2           110    3             6
 * c           1           1110   4             4
 * d           1           1111   4             4
 * Total bits                                   23
 *
 * Encoded text = 23 bits, compared with 11 x 8 = 88 bits without compression, so 65 bits (73.86%) are
 * saved.
 *
 * Complexity Analysis
 * Let m be the length of the text and n the number of distinct characters.
 *
 * Time Complexity
 * - Counting frequencies: O(m).
 * - Inserting the n leaf nodes into the heap: O(n log n).
 * - Building the tree: there are n - 1 merges, and each merge does two removals and one insertion on
 * the heap, each costing O(log n), so this is O(n log n).
 * - Generating the codes: the tree has 2n - 1 nodes and each is visited once, so O(n) node visits.
 * - Encoding the text: O(m x average code length).
 * The tree building dominates, so the time complexity is O(n log n) for n distinct characters (plus O(m)
 * to read and encode the text).
 *
 * Space Complexity
 * The heap, the tree (2n - 1 nodes) and the code table each use O(n) space. The encoded text needs
 * O(m) space. So the space complexity is O(n) for the algorithm itself.
 *
 * Why the greedy method is optimal
 * The two least frequent characters can always be made siblings at the deepest level of an optimal
 * tree. Exchanging a rare character with a more frequent one placed deeper can only keep the total
 * cost the same or reduce it. Merging them into one node and repeating on the smaller problem
 * therefore always leads to a tree with the minimum total encoded length.
 *
 * Fixed-length vs Huffman encoding
 *                Fixed-length (e.g. 8 bits)   Huffman encoding
 * Code length    Same for every character     Short for frequent, long for rare characters
 * Compression    None                         Yes (lossless)
 * Method         Direct table                 Greedy, using a min-heap and a tree
 * abracadabra    88 bits                      23 bits
 *
 * Viva Questions and Answers
 * Q1. What is Huffman encoding?
 * A lossless data compression method that gives variable-length binary codes to characters:
 * frequent characters get short codes and rare characters get long codes.
 *
 * Q2. What is the greedy choice in Huffman encoding?
 * At every step, remove the two nodes with the smallest frequencies and merge them into one new
 * node. The choice is never changed later.
 *
 * Q3. Why is the greedy method optimal here?
 * The two least frequent characters can always be siblings at the deepest level of an optimal tree.
 * Merging them and repeating gives a tree with the minimum total encoded length.
 *
 * Q4. Which data structure is used and why?
 * A min-heap (PriorityQueue in Java). It gives the node with the smallest frequency in O(log n) time,
 * and we need the two smallest nodes n - 1 times.
 *
 * Q5. What is the prefix property?
 * No code is the beginning of another code. This is true because characters are only at the leaves of
 * the tree. It lets the encoded text be decoded without separators.
 *
 * Q6. Why do frequent characters get shorter codes?
 * They are merged last, so they stay near the root of the tree, and the code length equals the depth
 * of the leaf.
 *
 * Q7. What is the time complexity?
 * O(n log n) for n distinct characters, because there are n - 1 merges and each heap operation costs
 * O(log n).
 *
 * Q8. What is the space complexity?
 * O(n) for the heap, the tree and the code table.
 *
 * Q9. If there are n distinct characters, how many nodes does the tree have?
 * 2n - 1 nodes in total: n leaf nodes and n - 1 internal nodes.
 *
 * Q10. Are Huffman codes unique?
 * No. When nodes have equal frequencies, they can be merged in different orders, and the codes
 * differ. The total encoded length is always the same minimum.
 *
 * Q11. What happens if the text has only one distinct character?
 * The tree is a single leaf, so its code would be empty. The program gives it the code 0, so aaaa
 * becomes 0000.
 *
 * Q12. What does your program do for empty input?
 * It prints 'Text cannot be empty.' and stops.
 *
 * Q13. How does decoding work?
 * Start at the root and read the bits one by one: 0 goes to the left child and 1 goes to the right child.
 * When a leaf is reached, output its character and go back to the root.
 *
 * Q14. Is Huffman encoding lossless or lossy?
 * Lossless. The original text can be recovered exactly.
 *
 * Q15. Why does the Node class have two constructors?
 * One creates a leaf node (a character and its frequency). The other creates an internal node (a
 * frequency and two children) with no character.
 *
 * Q16. How does the program know a node is a leaf?
 * A leaf has no children, so both left and right are null.
 *
 * Q17. What if all characters have the same frequency?
 * The tree becomes balanced and all codes have almost the same length, so there is little or no
 * saving. For example, 4 characters with equal frequency get 2 bits each.
 *
 * Q18. Where is Huffman coding used?
 * In compression formats such as ZIP and gzip (DEFLATE), JPEG and MP3.
 *
 * Conclusion
 * Huffman encoding uses a greedy strategy: it repeatedly merges the two least frequent nodes to build
 * a tree whose leaf codes are an optimal prefix code. The program runs in O(n log n) time and gives a
 * shorter encoding than fixed-length codes whenever character frequencies are uneven.
 */
