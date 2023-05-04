package visualizer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

public class Edge extends JComponent {
    private final Vertex node1;
    private final Vertex node2;
    private final int weight;

    private final int x1;
    private final int y1;
    private final int x2;
    private final int y2;
    private Line2D line;
    private Edge oppositeEdge;

    private final JLabel label;

    public Edge(Vertex node1, Vertex node2, int weight) {
        this(node1, node2, weight, true);
    }

    public Edge(Vertex node1, Vertex node2, int weight, boolean validConnection) {
        this.node1 = node1;
        this.node2 = node2;
        this.weight = weight;

        this.label = new JLabel(String.valueOf(weight));

        x1 = node1.getX() + node1.getWidth() / 2;
        x2 = node2.getX() + node2.getWidth() / 2;
        y1 = node1.getY() + node1.getHeight() / 2;
        y2 = node2.getY() + node2.getHeight() / 2;

        initialize();
        setName(String.format("Edge <%s -> %s>", node1.getId(), node2.getId()));
        if (validConnection) {
            node1.getConnections().putIfAbsent(this, node2);
            node2.getConnections().putIfAbsent(this, node1);
        }
    }

    public void removeConnections() {
        node1.getConnections().remove(this);
        node2.getConnections().remove(this);
    }

    public int getWeight() {
        return weight;
    }

    public static void setOpposite(Edge edge1, Edge edge2) {
        edge1.oppositeEdge = edge2;
        edge2.oppositeEdge = edge1;
    }

    public JLabel getLabel() {
        return label;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(this.getForeground());
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setStroke(new BasicStroke(5));
        g2d.draw(line);
        g2d.dispose();
    }

    private void initialize() {
        int x = Math.min(x1, x2);
        int y = Math.min(y1, y2);

        int width = Math.max(x1, x2) - x;
        int height = Math.max(y1, y2) - y;

        line = new Line2D.Double(new Point2D.Double(x1 - x, y1 - y)
                , new Point2D.Double(x2 - x, y2 - y));

        setSize(width, height);
        setBounds(x, y, width, height);
        setForeground(Color.DARK_GRAY);
        setOpaque(false);
    }

    public int[] getLabelPosition() {
         int x = Math.max(x1, x2) - (Math.abs(x1 - x2) / 2);
         int y = Math.max(y1, y2) - (Math.abs(y1 - y2) / 2);
         return new int[]{x, y};
    }

    public Edge getOpposite() {
        return this.oppositeEdge;
    }

    public boolean wasHit(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        return (this.line.ptLineDist(x, y) < 5);
    }
}
