import java.util.EmptyStackException;
import java.util.Stack;

public class BacktrackingAlgo extends Algo {
    private Point exit = null;
    private Point book = null;
    private Point cloak = null;
    Stack<Point> passed = new Stack<>();

    public BacktrackingAlgo(Environment env) {
        super(env);
    }

    @Override
    public Output run() throws Exception {
        long start_time = System.nanoTime();

        while (true) {
            Point goTo = null;
            for (Point p : Actor.goPriority) {
                if (actor.canGo(p)) {
                    goTo = p;
                    break;
                }
            }
            if (goTo != null) {
                actor.go(goTo);
                passed.push(new Point(actor.getPose()));
            } else {
//                System.out.println("back");
                actor.goBack();
                if (passed.peek().equals(exit) || passed.peek().equals(cloak) || passed.peek().equals(book) ) passed.push(new Point(actor.getPose()));
                else {
                    try {
                        passed.pop();
                    } catch (EmptyStackException e) {
                        throw new Exception("Book and/or exit is not available");
                    }

                }
            }

            if (actor.cell().type == 2 && !actor.hasCloak) {
                cloak = new Point(actor.getPose());
                actor.hasCloak = true;
            }
            if (actor.cell().type == 3 && !actor.hasBook) {
                book = new Point(actor.getPose());
                actor.hasBook = true;
                if (exit != null) break;
            }
            if (actor.cell().type == 4) {
                exit = new Point(actor.getPose());
            }
//            env.printCurrentState();
            if (actor.cell().type == 4 && actor.hasBook) break;
        }

        Stack<Point> npassed = (Stack<Point>) passed.clone();

        if (!passed.peek().equals(exit)) {
            passed.pop();
            while (!passed.peek().equals(exit)) {
                npassed.push(passed.pop());
            }
            npassed.push(passed.pop());
        }

        long end_time = System.nanoTime();
        return new Output(
                env,
                "Backtracking",
                "Win",
                npassed,
                ((end_time - start_time)/ 1000000000.0)
        );
    }
}
