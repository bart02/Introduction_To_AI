public class Cell {
    public int type; // 0 - empty, 1 - Harry, 2 - cloak, 3 - book, 4 - exit, -2 - enemy, -1 - enemy's zone,
    public Point source;

    public int g = (Integer.MAX_VALUE / 3);
    public int h = (Integer.MAX_VALUE / 3);
    public boolean closed = false;

    public int getWeight(boolean useHeuristic) {
        return g + (useHeuristic ? h : 0);
    }

    public Cell(int type) {
        this.type = type;
    }

    public Cell() {
        type = 0;
    }

    @Override
    public String toString() {
        return "Cell{" +
                "type=" + type +
                '}';
    }
}
