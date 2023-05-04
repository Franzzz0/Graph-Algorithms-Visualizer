package visualizer;

import java.awt.*;
import java.util.*;
import java.util.List;

public class DijkstraAlgorithm extends Algorithm{
    private final Map<Vertex, Integer> pathsMap;
    private final List<Vertex> traverseList;
    private final Queue<NodePath> queue;

    public DijkstraAlgorithm(Graph graph) {
        super(graph);
        pathsMap = new HashMap<>();
        traverseList = new ArrayList<>();
        queue = new PriorityQueue<>(Comparator.comparingInt(NodePath::distance));
    }


    @Override
    void traverseNodes(Vertex currentNode) {
        currentNode.setForeground(Color.RED);
        NodePath source = new NodePath(currentNode, 0);
        queue.offer(source);
        pathsMap.put(currentNode, 0);
        traverse();
    }

    private void traverse() {
        while (!queue.isEmpty()) {
            Vertex currentNode = queue.poll().node();

            List<Edge> edges = new ArrayList<>(currentNode.getConnections().keySet());
            edges.sort(Comparator.comparingInt(Edge::getWeight));
            for (Edge edge : edges) {
                Vertex node = currentNode.getConnections().get(edge);
                if (!traverseList.contains(node)) {
                    int nodeDistance = pathsMap.get(currentNode) + edge.getWeight();
                    if (pathsMap.get(node) == null || pathsMap.get(node) > nodeDistance) {
                        pathsMap.put(node, nodeDistance);
                    }
                    queue.offer(new NodePath(node, nodeDistance));
                }
            }
            if (!traverseList.contains(currentNode)) {
                traverseList.add(currentNode);
            }
        }
    }

    @Override
    String getResult() {
        StringBuilder stringBuilder = new StringBuilder();
        traverseList.sort(Comparator.comparing(Vertex::getId));
        for (int i = 1; i < traverseList.size(); i++) {
            Vertex node = traverseList.get(i);
            stringBuilder.append(String.format("%s=%d", node.getId(), pathsMap.get(node)));
            if (i < traverseList.size() - 1) {
                stringBuilder.append(", ");
            }
        }
        return stringBuilder.toString();
    }

    private record NodePath(Vertex node, int distance) {
    }
}
