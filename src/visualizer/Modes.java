package visualizer;

public enum Modes {
    ADD_VERTEX ("Add a Vertex"),
    ADD_EDGE ("Add an Edge"),
    REMOVE_VERTEX ("Remove a Vertex"),
    REMOVE_EDGE ("Remove an Edge"),
    NONE ("None"),
    ALGORITHM ("None");

    private final String text;

    Modes(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
