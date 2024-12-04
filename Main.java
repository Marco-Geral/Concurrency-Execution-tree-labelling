/*import java.util.*;

public class Main {
    public static void main(String[] args) {
        Random r = new Random(100);
        int bound = 50000;
        int[] tests = { 2, 2 * 2, 2 * 2 * 2, 2 * 2 * 2 * 2, 2 * 2 * 2 * 2 * 2 };
        for (int i = 1; i <= 5; i++) {
            System.out.println("###");
            HashSet<Integer> seenSeeds = new HashSet<>();
            for (int j = 0; j < tests[i - 1]; j++) {
                Integer nextSeed = r.nextInt(bound);
                while (seenSeeds.contains(nextSeed)) {
                    nextSeed = r.nextInt(bound);
                }
                seenSeeds.add(nextSeed);
                tree(i, nextSeed);
            }
        }
        System.out.println("###");

    }

    public static void tree(int size, int seed) {

        int n = 1; // Number of operations for each thread
        List<Character> threads = List.of('A', 'B'); // Characters to use
        ExecutionTree e = new ExecutionTree(threads, n, seed);
        e.root.assignLabels();

        ExecutionTreeMemo e2 = new ExecutionTreeMemo(threads, n, seed);
        e2.root.assignLabels();
        System.out.println("Tree with each thread making " + size + " moves and with seed " + seed + ": "
                + compareTrees(e.root, e2.root));
    }

    public static boolean compareTrees(Node node1, NodeMemo node2) {
        if (node1 == null && node2 == null) {
            return true;
        }

        if (node1 == null || node2 == null) {
            return false;
        }

        // Compare the labels
        List<Label> labels1 = new ArrayList<>(node1.labels);
        List<Label> labels2 = new ArrayList<>(node2.labels);
        Collections.sort(labels1);
        Collections.sort(labels2);

        if (!labels1.equals(labels2)) {
            return false;
        }

        // Compare the number of children
        if (node1.children.size() != node2.children.size()) {
            return false;
        }

        // Compare the children recursively
        for (int i = 0; i < node1.children.size(); i++) {
            Node child1 = node1.children.get(i);
            NodeMemo child2 = node2.children.get(i);
            if (!compareTrees(child1, child2)) {
                return false;
            }
        }

        return true;
    }
}*/
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Random r = new Random(100);
        int bound = 50000;
        int[] tests = { 2, 2 * 2, 2 * 2 * 2, 2 * 2 * 2 * 2, 2 * 2 * 2 * 2 * 2 };
        for (int i = 1; i <= 5; i++) {
            System.out.println("###");
            HashSet<Integer> seenSeeds = new HashSet<>();
            for (int j = 0; j < tests[i - 1]; j++) {
                Integer nextSeed = r.nextInt(bound);
                while (seenSeeds.contains(nextSeed)) {
                    nextSeed = r.nextInt(bound);
                }
                seenSeeds.add(nextSeed);
                tree(i, nextSeed);
            }
        }
        System.out.println("###");
    }

    public static void tree(int size, int seed) {
        int n = 1; // Number of operations for each thread
        List<Character> threads = Arrays.asList('A', 'B'); // Characters to use
        ExecutionTree e = new ExecutionTree(threads, n, seed);
        e.root.assignLabels();  // Label the tree starting from the root

        System.out.println("Tree with each thread making " + size + " moves and with seed " + seed + ":");
        e.root.printTree("", true);  // Print the labeled tree
    }
}

