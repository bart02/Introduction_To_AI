public abstract class Actor {
    protected Point pose;
    protected final Point initialPose;
    protected Environment env;
    public boolean hasBook = false;
    public boolean hasCloak = false;

    public Point[] goPriority = new Point[]{new Point(1,1), new Point(1,-1), new Point(-1,-1), new Point(-1,1), new Point(1, 0), new Point(0, 1), new Point(-1, 0), new Point(0, -1)};

    /**
     * Actor's constructor
     *
     * @param pose initial pose of created actor
     * @param env environment used by created actor
     */
    public Actor(Point pose, Environment env) {
        this.pose = pose;
        this.initialPose = new Point(pose);
        this.env = env;
    }

    /**
     * Check the possibility to go on vector
     *
     * @param dx vector's X coordinate
     * @param dy vector's Y coordinate
     */
    public abstract boolean canGo(int dx, int dy);

    /**
     * Move actor to point (Low-level actor's move)
     *
     * @param x point's X coordinate
     * @param y point's Y coordinate
     * @throws GameException if actor lose the game this move
     */
    public void move(int x, int y) throws GameException {
        pose.move(x, y);
        if (env.checkLose()) throw new GameException("Actor has died");
    }

    /**
     * Move actor on vector and save old position to new {@link Cell#source}
     *
     * @param dx vector's X coordinate
     * @param dy vector's Y coordinate
     * @throws GameException if actor lose the game this move
     */
    public void go(int dx, int dy) throws GameException {
        if (!canGo(dx, dy)) throw new GameException("Actor can't go here");
        Point old = new Point(pose);
        pose.translate(dx, dy);
        env.get(pose).source = old;
        if (env.checkLose()) throw new GameException("Actor has died");
    }

    /**
     * Proxy for {@link #canGo(int, int)}
     */
    public boolean canGo(Point vector){
        return canGo(vector.x, vector.y);
    }

    /**
     * Proxy for {@link #go(int, int)}
     */
    public void go(Point vector) throws GameException {
        go(vector.x, vector.y);
    }

    /**
     * Proxy for {@link #move(int, int)}
     */
    public void move(Point vector) throws GameException {
        move(vector.x, vector.y);
    }

    /**
     * Move actor back in Backtracking based algorithms
     *
     * @throws GameException if actor can't move back (stuck in start point)
     */
    public void goBack() throws GameException {
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

    /**
     * Implementation of {@link Actor} for the 1 variant (scenario)
     */
    public static class FirstActor extends Actor {
        public FirstActor(Point pose, Environment env) {
            super(pose, env);
        }

        public boolean canGo(int dx, int dy){
            Point n = pose.translateNew(dx, dy);
            if (n.x < 0 || n.y < 0 || n.x >= env.getSize() || n.y >= env.getSize()) return false;
            if (env.get(pose).source != null && env.get(pose).source.equals(n)) return false;
            if (env.get(n).source != null) return false;
            return env.get(n).type >= (hasCloak ? -1 : 0);
        }
    }

    /**
     * Implementation of {@link Actor} for the 2 variant (scenario).
     *
     * This actor has map memory, and can memoize percepted {@link Cell}s
     */
    public static class SecondActor extends Actor {
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

        /**
         * Memoization of percepted {@link Cell}s
         * (should be called each move of actor)
         */
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
        public void go(Point vector) throws GameException {
            super.go(vector);
            fillMap();
        }

        @Override
        public void move(Point vector) throws GameException {
            super.move(vector);
            fillMap();
        }
    }
}
