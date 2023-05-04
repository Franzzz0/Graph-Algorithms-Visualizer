package visualizer;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Graph extends JPanel {
    private Modes mode;
    private final MouseListener mouseListener;
    private final JLabel displayLabel;
    private Vertex selectedNode;

    public Graph(JLabel displayLabel) {
        super(null);
        this.displayLabel = displayLabel;

        setName("Graph");
        setSize(800, 600);
        setBackground(Color.WHITE);
        setOpaque(false);
        mouseListener = new MouseListener(this);
        addMouseListener(mouseListener);
    }
    public Modes getMode() {
        return mode;
    }

    public void setDisplayLabel(String text) {
        displayLabel.setText(text);
    }

    public void setMode(Modes mode) {
        if (this.mode == Modes.ALGORITHM) {
            displayLabel.setText("");
            resetHighlight();
            resetSelectedNode();
        } else {
            resetSelectedNode();
        }
        this.mode = mode;
    }

    public void addVertex(int x, int y, String name) {
        Vertex vertex = new Vertex(name);
        vertex.setBounds(x - 25, y - 25, 50, 50);
        vertex.addMouseListener(mouseListener);
        add(vertex);
        repaint();
    }

    public void addEdge(Vertex node1, Vertex node2, int weight) {
        Edge edge1 = new Edge(node1, node2, weight);
        edge1.addMouseListener(mouseListener);
        Edge edge2 = new Edge(node2, node1, weight, false);
        edge2.addMouseListener(mouseListener);
        JLabel weightLabel = edge1.getLabel();
        Edge.setOpposite(edge1, edge2);
        int[] labelPos = edge1.getLabelPosition();
        weightLabel.setBounds(labelPos[0], labelPos[1], 50, 50);
        weightLabel.setName(String.format("EdgeLabel <%s -> %s>", node1.getId(), node2.getId()));
        add(edge1);
        add(edge2);
        add(weightLabel);
        repaint();
    }

    public void reset() {
        Arrays.stream(this.getComponents()).forEach(this::remove);
    }

    public void removeVertex(Vertex vertex) {
        List<Edge> edges = new ArrayList<>(vertex.getConnections().keySet());
        edges.forEach(this::removeEdges);
        this.remove(vertex);
        repaint();
    }

    public void removeEdges(Edge edge) {
        Edge oppositeEdge = edge.getOpposite();
        removeEdge(oppositeEdge);
        removeEdge(edge);
    }

    private void removeEdge(Edge edge) {
        edge.removeConnections();
        this.remove(edge.getLabel());
        this.remove(edge);
        repaint();
    }

    public Vertex getSelectedNode() {
        return selectedNode;
    }

    public void setSelectedNode(Vertex selectedNode) {
        this.selectedNode = selectedNode;
    }

    public void resetSelectedNode() {
        if (selectedNode == null) return;
        selectedNode.setForeground(Color.LIGHT_GRAY);
        setSelectedNode(null);
    }

    private void resetHighlight() {
        for (Component component: this.getComponents()) {
            if (component.getClass() == Vertex.class) {
                component.setForeground(Color.LIGHT_GRAY);
            } else if (component.getClass() == Edge.class) {
                component.setForeground(Color.DARK_GRAY);
            }
        }
    }
}
