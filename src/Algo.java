public abstract class Algo {
    protected final Environment env;
    protected final Actor actor;

    public Algo(Environment env) {
        this.env = env;
        actor = env.actor;
    }

    public abstract Output run() throws GameException;
}
