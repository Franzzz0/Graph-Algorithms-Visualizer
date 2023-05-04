package visualizer;

import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class DFSAlgorithm extends Algorithm{
    private final List<Vertex> traverseList;

    public DFSAlgorithm(Graph graph) {
        super(graph);
        traverseList = new ArrayList<>();
    }

    @Override
    public void traverseNodes(Vertex currentNode) {
        currentNode.setForeground(Color.YELLOW);
        traverseList.add(currentNode);
        if (currentNode.getConnections().isEmpty()) return;

        try {
            TimeUnit.MILLISECONDS.sleep(500);
        } catch (InterruptedException e) {
            System.out.println("Algorithm traversal interrupted");
        }

        List<Edge> edges = new ArrayList<>(currentNode.getConnections().keySet());
        edges.sort(Comparator.comparingInt(Edge::getWeight));
        for (Edge edge : edges) {
            Vertex nextNode = currentNode.getConnections().get(edge);
            if (!traverseList.contains(nextNode)) {
                traverseNodes(nextNode);
            }
        }
    }

    @Override
    public String getResult() {
        StringBuilder stringBuilder = new StringBuilder("DFS : ");
        for (int i = 0; i < traverseList.size(); i++) {
            Vertex nodeVisited = traverseList.get(i);
            stringBuilder.append(nodeVisited.getId());
            if (i < traverseList.size() - 1) {
                stringBuilder.append(" -> ");
            }
        }
        return stringBuilder.toString();
    }
}
