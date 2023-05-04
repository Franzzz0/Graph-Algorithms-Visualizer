package visualizer;

public enum AlgorithmsEnum {
    DFS ("Depth-First Search"),
    BFS ("Breadth-First Search"),
    DIJKSTRA ("Dijkstra's Algorithm"),
    PRIM ("Prim's Algorithm");

    private final String name;

    AlgorithmsEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
