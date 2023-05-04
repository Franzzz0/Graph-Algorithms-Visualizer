package visualizer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MouseListener extends MouseAdapter {
    private final Graph graph;

    public MouseListener(Graph graph) {
        this.graph = graph;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        switch (graph.getMode()) {
            case ADD_VERTEX -> addVertex(e);
            case ADD_EDGE -> addEdge(e);
            case REMOVE_VERTEX -> removeVertex(e);
            case REMOVE_EDGE -> removeEdge(e);
            case ALGORITHM -> selectNode(e);
        }
    }

    private void removeEdge(MouseEvent e) {
        if (e.getSource().getClass() != Edge.class) return;
        Edge edge = (Edge) e.getSource();
        if (edge.wasHit(e)) {
            this.graph.removeEdges(edge);
        }
    }

    private void removeVertex(MouseEvent e) {
        if (e.getSource().getClass() != Vertex.class) return;
        this.graph.removeVertex((Vertex) e.getSource());
    }

    private void selectNode(MouseEvent e) {
        if (e.getSource().getClass() == Vertex.class && graph.getSelectedNode() == null) {
            Vertex node = (Vertex) e.getSource();
            node.setForeground(Color.YELLOW);
            graph.setSelectedNode((Vertex) e.getSource());
            graph.setDisplayLabel("Please wait...");
        }
    }

    private void addEdge(MouseEvent e) {
        Vertex node;
        if (e.getSource().getClass() != Vertex.class) {
            return;
        } else {
            node = (Vertex) e.getSource();
        }
        if (graph.getSelectedNode() == null) {
            graph.setSelectedNode(node);
            node.setForeground(Color.YELLOW);
        } else {
            int weight;
            while(true) {
                String input = JOptionPane.showInputDialog(null, "Enter Weight"
                        , JOptionPane.QUESTION_MESSAGE);
                if (input == null) {
                    graph.resetSelectedNode();
                    return;
                }
                try {
                    weight = Integer.parseInt(input);
                } catch (NumberFormatException ex) {
                    continue;
                }
                break;
            }
            graph.addEdge(graph.getSelectedNode(), node, weight);
            graph.getSelectedNode().setForeground(Color.LIGHT_GRAY);
            graph.setSelectedNode(null);
        }
    }

    private void addVertex(MouseEvent e) {
        graph.resetSelectedNode();
        if (e.getSource().getClass() == Vertex.class) return;
        int x = e.getX();
        int y = e.getY();
        String name;
        while (true) {
            name = JOptionPane.showInputDialog(null
                    , "Enter the Vertex ID (Should be 1 char):", "Vertex", JOptionPane.QUESTION_MESSAGE);
            if (name != null && (name.length() != 1 || name.isBlank())) {
                continue;
            }
            break;
        }
        if (name == null) return;
        this.graph.addVertex(x, y, name);
    }
}
