public class StatisticalAnalysis {
    public static void main(String[] args) throws Exception {
        double bt_len = 0;
        double as_len = 0;
        double bt_tim = 0;
        double as_tim = 0;
        int bt_win = 0;
        int as_win = 0;
        int all = 0;
        for (int i = 0; i < 10000; i++) {
            Input input = new Input();
            input.generate(2);
            all++;

            try {
                Algo algo0 = new BacktrackingAlgo(new Environment(9, input));
                Output o = algo0.run();
                bt_win++;
                bt_len += o.path.size();
                bt_tim += o.time;
            } catch (GameException e) {
                System.out.println("Algorithm: Backtracking");
                System.out.println("Outcome: Lose (" + e.getMessage() + ")");
            }
            System.out.println();

            try {
                Algo algo1 = new AStarAlgo(new Environment(9, input));
                Output o = algo1.run();
                as_win++;
                as_len += o.path.size();
                as_tim += o.time;
            } catch (GameException e) {
                System.out.println("Algorithm: A*");
                System.out.println("Outcome: Lose (" + e.getMessage() + ")");
            }
            System.out.println();
        }
        System.out.println(all);
        System.out.println();
        System.out.println(bt_win);
        System.out.println(bt_len / bt_win);
        System.out.println(bt_tim / bt_win);
        System.out.println();
        System.out.println(as_win);
        System.out.println(as_len / as_win);
        System.out.println(as_tim / as_win);

    }
}
