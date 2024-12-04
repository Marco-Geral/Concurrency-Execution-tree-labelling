import java.util.*;

class Node {
    Integer value;
    String path;
    List<Node> children;
    List<Label> labels;
    HashSet<Integer> childrenValues;

    public Node(Integer value, String path) {
        this.value = value;
        this.path = path;
        this.children = new ArrayList<>();
        this.labels = new ArrayList<>();
        this.childrenValues = new HashSet<>();
    }

    public Node(String path) {
        this.value = null;
        this.path = path;
        this.children = new ArrayList<>();
        this.labels = new ArrayList<>();
        this.childrenValues = new HashSet<>();
    }

    public void addChild(Node child) {
        this.children.add(child);
        this.childrenValues.add(child.value);
    }

    public void addLabel(Label l) {
        this.labels.add(l);
    }

    public void assignLabels() {
        if (this.isFinal()) {
            this.labels.add(Label.FINAL);
        } else {
            Set<Integer> outcomes = new HashSet<>();
            for (Node child : this.children) {
                child.assignLabels(); // Recursive label assignment
                Integer outcome = child.getFinalOutcome();
                if (outcome != null) {
                    outcomes.add(outcome);
                }
            }
            if (outcomes.size() == 1) {
                this.labels.add(Label.UNIVALENT);
            } else if (outcomes.size() > 1) {
                this.labels.add(Label.BIVALENT);
                if (this.isCritical()) {
                    this.labels.add(Label.CRITICAL);
                }
            }
        }
    }

    public Integer getFinalOutcome() {
        if (labels.contains(Label.FINAL)) {
            return this.value;
        }

        Set<Integer> outcomes = new HashSet<>();
        for (Node child : children) {
            Integer outcome = child.getFinalOutcome();
            if (outcome != null) {
                outcomes.add(outcome);
            }
        }

        if (outcomes.size() == 1) {
            return outcomes.iterator().next();
        }

        return null;
    }

    private boolean isFinal() {
        return this.children.isEmpty();
    }

    private boolean isCritical() {
        if (!labels.contains(Label.BIVALENT)) return false;
        for (Node child : children) {
            if (child.labels.contains(Label.BIVALENT)) {
                return false; // If any child is bivalent, this node cannot be critical
            }
        }
        return true;
    }

    public void printTree(String prefix, boolean isTail) {
        System.out.println(
                prefix + (isTail ? "└── " : "├── ") + path + " Labels:" + labels + " value: (" + value + ")");
        for (int i = 0; i < children.size() - 1; i++) {
            children.get(i).printTree(prefix + (isTail ? "    " : "│   "), false);
        }
        if (children.size() > 0) {
            children.get(children.size() - 1).printTree(prefix + (isTail ? "    " : "│   "), true);
        }
    }
}


public class ExecutionTree {
    public Random r;
    public Node root;

    public void assignLabels() {
        root.assignLabels();  // Call the assignLabels method in the Node class
    }

    public ExecutionTree(List<Character> threads, int n, int seed) {
        this.r = new Random(seed);
        int[] counts = new int[threads.size()]; // Array to keep track of counts

        this.root = new Node("");

        // Start the generation with each character
        for (int i = 0; i < threads.size(); i++) {
            counts[i]++;
            root.addChild(generateTree(threads.get(i) + "", counts, n, threads));
            counts[i]--; // Backtrack
        }

        root.addLabel(Label.INITIAL);
    }

    public Node generateTree(String path, int[] counts, int n, List<Character> chars) {
        boolean isComplete = true;
        for (int count : counts) {
            if (count < n) {
                isComplete = false;
                break;
            }
        }
        if (isComplete) {
            return new Node(r.nextInt(counts.length), path);
        }

        Node node = new Node(path);

        for (int i = 0; i < chars.size(); i++) {
            if (counts[i] < n) {
                counts[i]++;
                node.addChild(generateTree(path + chars.get(i), counts, n, chars));
                counts[i]--; // Backtrack
            }
        }
        return node;
    }
}