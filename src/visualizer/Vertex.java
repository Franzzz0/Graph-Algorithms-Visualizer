package visualizer;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class Vertex extends JPanel {
    private final String id;
    private final Map<Edge, Vertex> connections;
    public Vertex(String id) {
        super(null);
        this.id = id;
        connections = new HashMap<>();
        setForeground(Color.LIGHT_GRAY);
        setName("Vertex " + id);
        setSize(50, 50);
        setOpaque(false);
        JLabel label = new JLabel(id);
        label.setName("VertexLabel " + id);
        label.setBounds(20, 20, 10 ,10);
        add(label);
    }

    public Map<Edge, Vertex> getConnections() {
        return connections;
    }

    public String getId() {
        return id;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(this.getForeground());
        g.fillOval(0, 0, 50, 50);
    }
}
