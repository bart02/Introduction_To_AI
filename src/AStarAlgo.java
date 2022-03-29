import java.util.*;
import java.util.stream.Collectors;

public class AStarAlgo extends Algo {
    private Point exit = null;
    private Point book = null;
    private Point cloak = null;

    public AStarAlgo(Environment env) {
        super(env);
    }

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

    private void researchNearestCells(Point end) {
        for (Point p : Actor.goPriority) {
            if (actor.canGo(p)) {
                Cell goTo = env.get(actor.getPose().translateNew(p));
                goTo.source = new Point(actor.getPose());
                goTo.g = Integer.min(goTo.g, actor.cell().g + 1);
                if (end != null) goTo.h = actor.getPose().translateNew(p).manhattanDistance(end);
            }
        }
    }

    private void findAll() throws Exception {
        exit = null;
        book = null;
        cloak = null;

        while (true) {
            Point minPoint = findMinPoint(false);
            if (minPoint == null) break;  // All available cells are closed, exit
            actor.move(minPoint);

            researchNearestCells(null);
            actor.cell().closed = true;

            if (actor.cell().type == 2) {
                cloak = new Point(actor.getPose());
            }
            if (actor.cell().type == 3) {
                book = new Point(actor.getPose());
            }
            if (actor.cell().type == 4) {
                exit = new Point(actor.getPose());
            }
//            env.printWeight(false);
//            System.out.println();

            if (book != null && exit != null && cloak != null) break;
        }
        if (book == null || exit == null) throw new GameException("Book and/or exit is not available");
    }

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

    private List<Point> findPath(Point start, Point end) throws Exception {
        env.cleanWeights();
        env.get(start).g = 0;
        env.get(start).h = start.manhattanDistance(end);

        do {
            Point minPoint = findMinPoint(true);
            if (minPoint == null) throw new GameException("There are no such way");
            actor.move(minPoint);

            researchNearestCells(end);
            actor.cell().closed = true;

//            env.printWeight(true);
//            System.out.println();
        } while (!actor.getPose().equals(end));

        return getPath(end);
    }

    private List<Point> findPath(Point... start) throws Exception {
        ArrayList<List<Point>> paths = new ArrayList<>();
        actor.hasCloak = false;
        for (int i = 0; i < start.length - 1; i++) {
            if (env.get(start[i]).type == 2) actor.hasCloak = true;
            paths.add(findPath(start[i], start[i + 1]));
        }
        return paths.stream().flatMap(Collection::stream).collect(Collectors.toList());
    }

    @Override
    public Output run() throws Exception {
        long start_time = System.nanoTime();

        findAll();

        ArrayList<List<Point>> paths = new ArrayList<>();
        paths.add(findPath(actor.getInitialPose(), book, exit));
        if (cloak != null) {
            paths.add(findPath(actor.getInitialPose(), cloak, book, exit));
            paths.add(findPath(actor.getInitialPose(), book, cloak, exit));
        }

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
