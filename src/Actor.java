public abstract class Actor {
    protected Point pose;
    protected final Point initialPose;
    protected Environment env;
    public boolean hasBook = false;
    public boolean hasCloak = false;

    public Point[] goPriority = null;

    public Actor(Point pose, Environment env) {
        this.pose = pose;
        this.initialPose = new Point(pose);
        this.env = env;
    }

    public abstract boolean canGo(int dx, int dy);

    public void go(int dx, int dy) throws Exception {
        if (!canGo(dx, dy)) throw new GameException("Actor can't go here");
        Point old = new Point(pose);
        pose.translate(dx, dy);
        env.get(pose).source = old;
        if (env.checkLose()) throw new GameException("Actor has died");
    }

    public void move(int x, int y) throws Exception {
        pose.move(x, y);
        if (env.checkLose()) throw new GameException("Actor has died");
    }

    public boolean canGo(Point vector){
        return canGo(vector.x, vector.y);
    }

    public void go(Point vector) throws Exception {
        go(vector.x, vector.y);
    }

    public void move(Point vector) throws Exception {
        move(vector.x, vector.y);
    }

    public void goBack() throws Exception {
        Point old = new Point(pose);
        try {
            pose.move(env.get(pose).source.x, env.get(pose).source.y);
        } catch (NullPointerException e) {
            throw new GameException("Actor can't move");
        }
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
