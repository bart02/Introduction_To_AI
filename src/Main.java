public class Main {
    public static void main(String[] args) throws Exception {
        Input input = new Input();
        input.readFromStdin();

        Algo algo = new AStarAlgo(new Environment(9, input));
        Output out = algo.run();

        out.print();
    }
}
