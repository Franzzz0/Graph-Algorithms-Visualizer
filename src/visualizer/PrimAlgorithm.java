package visualizer;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class PrimAlgorithm extends Algorithm{
    private final List<Vertex> traverseList;
    private final List<NodePair> nodePairs;
    private final Queue<Edge> queue;

    public PrimAlgorithm(Graph graph) {
        super(graph);
        traverseList = new ArrayList<>();
        nodePairs = new ArrayList<>();
        queue = new PriorityQueue<>(Comparator.comparingInt(Edge::getWeight));
    }

    @Override
    void traverseNodes(Vertex currentNode) {
        traverseList.add(currentNode);
        currentNode.setForeground(Color.YELLOW);

        try {
            TimeUnit.MILLISECONDS.sleep(500);
        } catch (InterruptedException e) {
            System.out.println("Algorithm traversal interrupted");
        }

        Vertex parent = null;
        Vertex child = null;
        Edge chosenEdge = null;

        List<Edge> edges = new ArrayList<>(currentNode.getConnections().keySet());
        edges.stream()
                .filter(edge -> !traverseList.contains(currentNode.getConnections().get(edge)))
                .forEach(queue::offer);
        do {
            Edge nextEdge = queue.poll();
            if (nextEdge == null) return;
            for (Vertex node : traverseList) {
                Vertex nextNode = node.getConnections().get(nextEdge);
                if (nextNode != null && !traverseList.contains(nextNode)) {
                    parent = node;
                    child = nextNode;
                    chosenEdge = nextEdge;
                    break;
                }
            }
        } while (chosenEdge == null);
        chosenEdge.setForeground(Color.YELLOW);
        nodePairs.add(new NodePair(parent, child));
        traverseNodes(child);
    }

    @Override
    String getResult() {
        nodePairs.sort(Comparator.comparing(pair -> pair.child().getId()));
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < nodePairs.size(); i++) {
            stringBuilder.append(String.format("%s=%s",
                    nodePairs.get(i).child.getId(),
                    nodePairs.get(i).parent.getId()));
            if (i < nodePairs.size() - 1) stringBuilder.append(", ");
        }
        return stringBuilder.toString();
    }

    private record NodePair(Vertex parent, Vertex child) {
    }
}
