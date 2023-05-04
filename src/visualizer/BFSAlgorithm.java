package visualizer;

import java.awt.*;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class BFSAlgorithm extends Algorithm{
    private final List<Vertex> visitedList;
    private final ArrayDeque<Vertex> traverseStack;

    public BFSAlgorithm(Graph graph) {
        super(graph);
        visitedList = new ArrayList<>();
        traverseStack = new ArrayDeque<>();
    }

    @Override
    void traverseNodes(Vertex currentNode) {
        currentNode.setForeground(Color.YELLOW);
        visitedList.add(currentNode);
        if (currentNode.getConnections().isEmpty()) return;

        try {
            TimeUnit.MILLISECONDS.sleep(500);
        } catch (InterruptedException e) {
            System.out.println("Algorithm traversal interrupted");
        }

        List<Edge> edges = new ArrayList<>(currentNode.getConnections().keySet());
        edges.sort(Comparator.comparingInt(Edge::getWeight));
        for (Edge edge : edges) {
            Vertex node = currentNode.getConnections().get(edge);
            if (!visitedList.contains(node) && !traverseStack.contains(node)) {
                traverseStack.push(node);
            }
        }
        Vertex next = traverseStack.pollLast();
        if (next != null) traverseNodes(next);
    }

    @Override
    String getResult() {
        StringBuilder stringBuilder = new StringBuilder("BFS : ");
        for (int i = 0; i < visitedList.size(); i++) {
            Vertex nodeVisited = visitedList.get(i);
            stringBuilder.append(nodeVisited.getId());
            if (i < visitedList.size() - 1) {
                stringBuilder.append(" -> ");
            }
        }
        return stringBuilder.toString();
    }
}
