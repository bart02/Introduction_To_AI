import java.util.List;
import java.util.Stack;

public class Main {
    public static Environment env;

    public static void backtracking() throws Exception {
        Actor actor = env.actor;
        Stack<Point> passed = new Stack<>();
        boolean bookTaken = false;
        boolean cloakTaken = false;
        Point exit = null;

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
                bookTaken = false;
                cloakTaken = false;
            } else {
                System.out.println("back");
                actor.goBack();
                if (bookTaken || cloakTaken) passed.push(new Point(actor.getPose()));
                else passed.pop();
            }

            if (actor.cell().type == 2) {
                cloakTaken = true;
                actor.hasCloak = true;
            }
            if (actor.cell().type == 3) {
                bookTaken = true;
                actor.hasBook = true;
                if (exit != null) break;
            }
            if (actor.cell().type == 4) {
                exit = new Point(actor.getPose());
            }
            env.printCurrentState();
            if (actor.cell().type == 4 && actor.hasBook) break;
        }

        Stack<Point> npassed = (Stack<Point>) passed.clone();

        passed.pop();
        while (!passed.peek().equals(exit)) {
            npassed.push(passed.pop());
        }
        npassed.push(passed.pop());

        System.out.println(npassed);
    }


    public static void main(String[] args) throws Exception {
        Input input = new Input();
        input.readFromStdin();

        AStarAlgo algo = new AStarAlgo(new Environment(9, input));
        Output aStarOut = algo.run();

        System.out.println(aStarOut);
    }
}
