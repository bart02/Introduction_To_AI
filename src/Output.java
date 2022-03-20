import java.util.List;

public class Output {
    private Environment env;
    private String algo_name;
    private String outcome;
    private List<Point> path;
    private double time;

    public Output(Environment env, String algo_name, String outcome, List<Point> path, double time) {
        this.env = env;
        this.algo_name = algo_name;
        this.outcome = outcome;
        this.path = path;
        this.time = time;
    }

    public void print() {
        System.out.println("Algorithm: " + algo_name);
        System.out.println("Outcome: " + outcome);
        System.out.println("Number of steps: " + path.size());
        System.out.println("Path: " + path);
        System.out.println("Time: " + time);
    }

    @Override
    public String toString() {
        return "Output{" +
                "env=" + env +
                ", algo_name='" + algo_name + '\'' +
                ", outcome='" + outcome + '\'' +
                ", path=" + path +
                ", time=" + time +
                '}';
    }
}
