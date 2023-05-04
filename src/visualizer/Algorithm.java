package visualizer;

import java.util.concurrent.TimeUnit;

public abstract class Algorithm extends Thread {
    private final Graph graph;

    public Algorithm(Graph graph) {
        this.graph = graph;
    }

    public Graph getGraph() {
        return graph;
    }

    @Override
    public void run() {
        graph.setDisplayLabel("Please choose a starting vertex");
        Vertex selectedNode;
        while (true) {
            if (graph.getSelectedNode() != null) {
                selectedNode = graph.getSelectedNode();
                break;
            }
            try {
                TimeUnit.MILLISECONDS.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        traverseNodes(selectedNode);
        graph.setDisplayLabel(getResult());
    }

    abstract void traverseNodes(Vertex startNode);
    abstract String getResult();
}
