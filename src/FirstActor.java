public class FirstActor extends Actor {
    public FirstActor(Point pose, Environment env) {
        super(pose, env);
        goPriority = new Point[]{new Point(1,1), new Point(1,-1), new Point(-1,-1), new Point(-1,1), new Point(1, 0), new Point(0, 1), new Point(-1, 0), new Point(0, -1)};
    }

    public boolean canGo(int dx, int dy){
        Point n = pose.translateNew(dx, dy);
        if (n.x < 0 || n.y < 0 || n.x >= env.getSize() || n.y >= env.getSize()) return false;
        if (env.get(pose).source != null && env.get(pose).source.equals(n)) return false;
        if (env.get(n).source != null) return false;
        return env.get(n).type >= (hasCloak ? -1 : 0);
    }
}
