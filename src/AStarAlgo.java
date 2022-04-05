import java.util.*;
import java.util.stream.Collectors;

public class AStarAlgo extends Algo {
    private Point exit = null;
    private Point book = null;
    private Point cloak = null;

    public AStarAlgo(Environment env) {
        super(env);
    }

    /**
     * Find the min-weight opened point
     *
     * @param useHeuristic see {@link Cell#getWeight(boolean)} and {@link AStarAlgo#researchNearestCells(Point)}
     * @return {@link Point} object, that has min-weight
     */
    private Point findMinPoint(boolean useHeuristic) {
        int minWeight = (Integer.MAX_VALUE / 3);
        Point minPoint = null;
        for (int i = 0; i < env.getSize(); i++) {
            for (int j = 0; j < env.getSize(); j++) {
                int weight = env.get(i, j).getWeight(useHeuristic);
                if (weight < minWeight && !env.get(i, j).closed) {
                    minWeight = weight;
                    minPoint = new Point(i, j);
                }
            }
        }
        return minPoint;
    }

    /**
     * Method researches all possible ways from {@link Actor#goPriority}
     *
     * @param end if not null, will use {@link Point#manhattanDistance(Point)} as heuristic
     */
    private void researchNearestCells(Point end) {
        for (Point p : actor.goPriority) {
            if (actor.canGo(p)) {
                Cell goTo = env.get(actor.getPose().translateNew(p));
                goTo.source = new Point(actor.getPose());
                goTo.g = Integer.min(goTo.g, actor.cell().g + 1);
                if (end != null) goTo.h = actor.getPose().translateNew(p).manhattanDistance(end);
            }
        }
    }

    /**
     * The method called first for scanning the field and finding coordinates of all game stuff
     *
     * @throws GameException in case of lose or not available book or exit
     */
    private void scan() throws GameException {
        exit = null;
        book = null;
        cloak = null;

        while (true) {
            Point minPoint = findMinPoint(false);
            if (minPoint == null) break;  // All available cells are closed, exit
            actor.move(minPoint);

            researchNearestCells(null);
            actor.cell().closed = true;

            if (actor.cell().type == 2 && !actor.hasCloak) {
                cloak = new Point(actor.getPose());
                actor.hasCloak = true;
                env.cleanWeights();
                env.get(cloak).g = 0;
                continue;
            }
            if (actor.cell().type == 3) book = new Point(actor.getPose());
            if (actor.cell().type == 4) exit = new Point(actor.getPose());
            if (book != null && exit != null && cloak != null) break;
        }
        if (book == null || exit == null) throw new GameException("Book and/or exit is not available");
    }

    /**
     * Iterates for each point in path (using {@link Cell#source}) and get the final path
     *
     * @param p End point, from which we will start iterating
     * @return list of {@link Point}s, not including start point
     */
    private List<Point> getPath(Point p) {
        Stack<Point> path = new Stack<>();
        do {
            path.push(p);
            p = env.get(p).source;
        } while (p != null);
        path.pop();
        Collections.reverse(path);
        return path;
    }

    /**
     * A* implementation for find the shortest path (with heuristic)
     *
     * @param start path's start point
     * @param end path's end point
     * @return path - list of {@link Point}s
     * @throws GameException in case of no way between start and end
     */
    private List<Point> findPath(Point start, Point end) throws GameException {
        env.cleanWeights();
        env.get(start).g = 0;
        env.get(start).h = start.manhattanDistance(end);

        do {
            Point minPoint = findMinPoint(true);
            if (minPoint == null) throw new GameException("There are no such way");
            actor.move(minPoint);

            researchNearestCells(end);
            actor.cell().closed = true;
        } while (!actor.getPose().equals(end));

        return getPath(end);
    }

    /**
     * VarArg function for {@link AStarAlgo#findPath(Point, Point)}
     */
    private List<Point> findPath(Point... p) throws GameException {
        ArrayList<List<Point>> paths = new ArrayList<>();
        actor.hasCloak = false;
        for (int i = 0; i < p.length - 1; i++) {
            if (env.get(p[i]).type == 2) actor.hasCloak = true;
            paths.add(findPath(p[i], p[i + 1]));
        }
        return paths.stream().flatMap(Collection::stream).collect(Collectors.toList());
    }

    @Override
    public Output run() throws GameException {
        long start_time = System.nanoTime();

        scan();

        ArrayList<List<Point>> paths = new ArrayList<>();
        if (cloak != null) {
            try {
                paths.add(findPath(actor.getInitialPose(), cloak, book, exit));
            } catch (GameException ignored) {}
            try {
                paths.add(findPath(actor.getInitialPose(), book, cloak, exit));
            } catch (GameException ignored) {}
        }
        try {
            paths.add(findPath(actor.getInitialPose(), book, exit));
        } catch (GameException ignored) {}

        if (paths.isEmpty()) throw new GameException("There are no such way");

        List<Point> min_path = Collections.min(paths, Comparator.comparingInt(List::size));

        long end_time = System.nanoTime();

        return new Output(
                env,
                "A*",
                "Win",
                min_path,
                ((end_time - start_time) / 1000000000.0)
        );
    }
}
