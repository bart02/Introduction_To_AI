public class ArtemBatalov {
    public static void main(String[] args) throws Exception {
        Input input = new Input();
        input.readFromStdin();

        try {
            Algo algo0 = new BacktrackingAlgo(new Environment(9, input));
            Output out0 = algo0.run();
            out0.print();
        } catch (Exception e) {
            System.out.println("Algorithm: Backtracking");
            System.out.println(e.getMessage());
        }


        System.out.println();

        try {
            Algo algo1 = new AStarAlgo(new Environment(9, input));
            Output out1 = algo1.run();
            out1.print();
        } catch (Exception e) {
            System.out.println("Algorithm: A*");
            System.out.println(e.getMessage());
        }
    }
}
