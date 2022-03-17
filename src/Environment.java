public class Environment {
    private final Cell[][] map;
    private final int size;
    public Actor actor;

    public Environment(int size) {
        map = new Cell[size][size];
        this.size = size;
        clean();
    }

    public Environment(int size, Input in) {
        this(size);
        setHarry(in.harry);
        setFilch(in.filch);
        setCat(in.cat);
        setBook(in.book);
        setCloak(in.cloak);
        setExit(in.exit);
    }

    public void clean() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                map[i][j] = new Cell();
            }
        }
    }

    public void cleanWeights() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                map[i][j].g = (Integer.MAX_VALUE / 3);
                map[i][j].h = (Integer.MAX_VALUE / 3);
                map[i][j].closed = false;
                map[i][j].source = null;
            }
        }
    }

    public void setEnemy(int x, int y, int zone) {
        int leftup_corner_x = x - zone;
        int leftup_corner_y = y - zone;
        int rightdown_corner_x = x + zone;
        int rightdown_corner_y = y + zone;
        for (int i = leftup_corner_x; i <= rightdown_corner_x; i++) {
            for (int j = leftup_corner_y; j <= rightdown_corner_y; j++) {
                map[i][j].type = -1;
            }
        }
        map[x][y].type = -2;
    }

    public void setFilch(Point c) {
        setEnemy(c.x, c.y, 2);
    }

    public void setCat(Point c) {
        setEnemy(c.x, c.y, 1);
    }

    public void setBook(Point c) {
        map[c.x][c.y].type = 3;
    }

    public void setCloak(Point c) {
        map[c.x][c.y].type = 2;
    }

    public void setExit(Point c) {
        map[c.x][c.y].type = 4;
    }

    public void setHarry(Point c) {
        map[c.x][c.y].type = 1;
        map[c.x][c.y].g = 0;
        actor = new Actor(c, this);
    }

    public Cell get(Point p) {
        return map[p.x][p.y];
    }

    public Cell get(int x, int y) {
        return map[x][y];
    }

    public void print() {
        print(null);
    }

    public void print(Point p) {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (p != null && p.equals(new Point(i,j))) s.append("#");
                else s.append(map[i][j].type);
                s.append("\t");
            }
            s.append("\n");
        }
        s.deleteCharAt(s.length() - 1);
        System.out.println(s);
    }

    public void printWeight(boolean useHeuristic) {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                s.append(map[i][j].getWeight(useHeuristic) >= (Integer.MAX_VALUE / 3) ? -1 : map[i][j].getWeight(useHeuristic));
                s.append("\t");
            }
            s.append("\n");
        }
        s.deleteCharAt(s.length() - 1);
        System.out.println(s);
    }

    public void printCurrentState() {
        System.out.println(actor);
        print(actor.getPose());
        System.out.println();
    }

    public int getSize() {
        return size;
    }
}
