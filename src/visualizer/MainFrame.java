package visualizer;

import javax.swing.*;
import java.util.EnumSet;

public class MainFrame extends JFrame {
    private final JLabel modeLabel;
    private final Graph graph;

    public MainFrame() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setName("Graph-Algorithms Visualizer");
        setTitle("Graph-Algorithms Visualizer");
        setLayout(null);

        modeLabel = new JLabel();
        modeLabel.setBounds(550, 20, 200, 20);
        modeLabel.setName("Mode");
        add(modeLabel);

        JLabel displayLabel = new JLabel();
        displayLabel.setBounds(50, 500, 700, 20);
        displayLabel.setName("Display");
        displayLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(displayLabel);

        graph = new Graph(displayLabel);

        this.setJMenuBar(getMenu());
        add(graph);
        setMode(Modes.ADD_VERTEX);
        setVisible(true);
    }

    private void setMode(Modes mode) {
        this.graph.setMode(mode);
        modeLabel.setText("Current Mode => " + mode.getText());
    }

    private void runAlgorithm(AlgorithmsEnum algorithm) {
        setMode(Modes.ALGORITHM);
        Algorithm alg = switch (algorithm) {
            case DFS -> new DFSAlgorithm(graph);
            case BFS -> new BFSAlgorithm(graph);
            case DIJKSTRA -> new DijkstraAlgorithm(graph);
            case PRIM -> new PrimAlgorithm(graph);
        };
        alg.start();
    }

    private void resetGraph() {
        this.graph.reset();
        setMode(Modes.ADD_VERTEX);
        graph.repaint();
    }

    private JMenuBar getMenu() {
        JMenuBar menuBar = new JMenuBar();
        menuBar.setName("MenuBar");

        JMenu fileMenu = getFileMenu();
        JMenu modeMenu = getModeMenu();
        JMenu algMenu = getAlgMenu();

        menuBar.add(fileMenu);
        menuBar.add(modeMenu);
        menuBar.add(algMenu);
        return menuBar;
    }

    private JMenu getAlgMenu() {
        JMenu algMenu = new JMenu("Algorithms");
        algMenu.setName("Algorithms");

        for (AlgorithmsEnum algorithm : AlgorithmsEnum.values()) {
            JMenuItem algItem = new JMenuItem(algorithm.getName());
            algItem.setName(algorithm.getName());
            algItem.addActionListener(action -> runAlgorithm(algorithm));
            algMenu.add(algItem);
        }
        return algMenu;
    }

    private JMenu getModeMenu() {
        JMenu modeMenu = new JMenu("Mode");
        modeMenu.setName("Mode");
        EnumSet<Modes> modes = EnumSet.range(Modes.ADD_VERTEX, Modes.NONE);
        for (Modes mode: modes) {
            JMenuItem modeItem = new JMenuItem(mode.getText());
            modeItem.addActionListener(action -> setMode(mode));
            modeItem.setName(mode.getText());
            modeMenu.add(modeItem);
        }
        return modeMenu;
    }

    private JMenu getFileMenu() {
        JMenu fileMenu = new JMenu("File");
        fileMenu.setName("File");
        JMenuItem reset = new JMenuItem("New");
        reset.setName("New");
        fileMenu.add(reset);
        reset.addActionListener(action -> resetGraph());
        JMenuItem exit = new JMenuItem("Exit");
        exit.setName("Exit");
        exit.addActionListener(action -> {
            setVisible(false);
            dispose();
        });
        fileMenu.add(exit);
        return fileMenu;
    }
}