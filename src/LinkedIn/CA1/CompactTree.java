package LinkedIn.CA1;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class CompactTree {
    class Node {
        List<Node> children;
        Node() {
            children = new LinkedList<Node>();
        }
        List<Node> getChildren() {
            return children;
        }
        void addChild(Node child) {
            children.add(child);
        }
        int size() {
            return children.size();
        }
    }
    public Node compact(Node root, int n) {
        Queue<Node> queueForExtracting = new LinkedList<>();
        Queue<Node> queueForBuilding = new LinkedList<>();
        queueForExtracting.offer(root);
        while (!queueForExtracting.isEmpty()) {
            Node cur = queueForExtracting.poll();
            queueForExtracting.addAll(cur.getChildren());
            cur.getChildren().clear();
            // construct new tree here
            if (!queueForBuilding.isEmpty()) {
                while (queueForBuilding.peek().size() >= n) {
                    queueForBuilding.remove();
                }
                queueForBuilding.peek().addChild(cur);
            }
            queueForBuilding.add(cur);
        }
        return root;
    }
}
