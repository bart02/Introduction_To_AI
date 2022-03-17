public class Actor {
    private Point pose;
    private final Point initialPose;
    private Environment env;
    public boolean hasBook = false;
    public boolean hasCloak = false;

    public static Point[] goPriority = new Point[]{new Point(1,1), new Point(1,-1), new Point(-1,-1), new Point(-1,1), new Point(1, 0), new Point(0, 1), new Point(-1, 0), new Point(0, -1)};

    public Actor(Point pose, Environment env) {
        this.pose = pose;
        this.initialPose = new Point(pose);
        this.env = env;
    }

    public void go(Point vector) throws Exception {
        go(vector.x, vector.y);
    }

    public void go(int dx, int dy) throws Exception {
        if (!canGo(dx, dy)) throw new Exception("Can't go here");
        Point old = new Point(pose);
        pose.translate(dx, dy);
        env.get(pose).source = old;
    }

    public void move(Point vector) throws Exception {
        move(vector.x, vector.y);
    }

    public void move(int x, int y) throws Exception {
        pose.move(x, y);
    }

    public void goBack() throws Exception {
        Point old = new Point(pose);
        pose.move(env.get(pose).source.x, env.get(pose).source.y);
    }

    public boolean canGo(Point vector){
        return canGo(vector.x, vector.y);
    }

    public boolean canGo(int dx, int dy){
        Point n = pose.translateNew(dx, dy);
        if (n.x < 0 || n.y < 0 || n.x >= env.getSize() || n.y >= env.getSize()) return false;
        if (env.get(pose).source != null && env.get(pose).source.equals(n)) return false;
        if (env.get(n).source != null) return false;
        return env.get(n).type >= (hasCloak ? -1 : 0);
    }

    public Cell cell(){
        return env.get(pose);
    }

    @Override
    public String toString() {
        return "Actor{" +
                "pose=" + pose +
                ", hasBook=" + hasBook +
                ", hasCloak=" + hasCloak +
                '}';
    }

    public Point getPose() {
        return pose;
    }

    public Point getInitialPose() {
        return initialPose;
    }
}
