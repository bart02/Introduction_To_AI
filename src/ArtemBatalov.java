public class ArtemBatalov {
    public static void main(String[] args) throws Exception {
        Input input = new Input();
        input.interactive();

        try {
            Algo algo0 = new BacktrackingAlgo(new Environment(9, input));
            algo0.run().print();
        } catch (GameException e) {
            System.out.println("Algorithm: Backtracking");
            System.out.println("Outcome: Lose (" + e.getMessage() + ")");
        }

        System.out.println();

        try {
            Algo algo1 = new AStarAlgo(new Environment(9, input));
            algo1.run().print();
        } catch (GameException e) {
            System.out.println("Algorithm: A*");
            System.out.println("Outcome: Lose (" + e.getMessage() + ")");
        }
    }
}
