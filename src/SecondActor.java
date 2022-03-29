public class SecondActor extends Actor {
    private final int[][] map;

    public SecondActor(Point pose, Environment env) {
        super(pose, env);

        goPriority = new Point[]{new Point(1, 0), new Point(0, 1), new Point(-1, 0), new Point(0, -1), new Point(1,1), new Point(1,-1), new Point(-1,-1), new Point(-1,1)};

        map = new int[env.getSize()][env.getSize()];
        for (int i = 0; i < env.getSize(); i++) {
            for (int j = 0; j < env.getSize(); j++) {
                map[i][j] = 0;
            }
        }
        fillMap();
    }

    private void fillMap() {
        for (Point p : new Point[]{
                new Point(2, -1),
                new Point(2, 0),
                new Point(2, 1),
                new Point(-2, -1),
                new Point(-2, 0),
                new Point(-2, 1),
                new Point(-1, 2),
                new Point(0, 2),
                new Point(1, 2),
                new Point(-1, -2),
                new Point(0, -2),
                new Point(1, -2),
        }) {
            Point n = pose.translateNew(p);
            if (n.x < 0 || n.y < 0 || n.x >= env.getSize() || n.y >= env.getSize()) continue;
            map[n.x][n.y] = env.get(n).type;
        }
    }

    @Override
    public boolean canGo(int dx, int dy) {
        Point n = pose.translateNew(dx, dy);
        if (n.x < 0 || n.y < 0 || n.x >= env.getSize() || n.y >= env.getSize()) return false;
        if (env.get(pose).source != null && env.get(pose).source.equals(n)) return false;
        if (env.get(n).source != null) return false;
        return map[n.x][n.y] >= (hasCloak ? -1 : 0);
    }

    @Override
    public void go(Point vector) throws Exception {
        super.go(vector);
        fillMap();
    }

    @Override
    public void move(Point vector) throws Exception {
        super.move(vector);
        fillMap();
    }
}
