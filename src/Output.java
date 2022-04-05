import java.util.List;

public class Output {
    private Environment env;
    public String algoName;
    public String outcome;
    public List<Point> path;
    public double time;

    public Output(Environment env, String algoName, String outcome, List<Point> path, double time) {
        this.env = env;
        this.algoName = algoName;
        this.outcome = outcome;
        this.path = path;
        this.time = time;
    }

    public void print() {
        System.out.println("Algorithm: " + algoName);
        System.out.println("Outcome: " + outcome);
        System.out.println("Number of steps: " + path.size());
        System.out.println("Path: " + path);
        System.out.println("Time: " + time + " s.");
    }

    @Override
    public String toString() {
        return "Output{" +
                "env=" + env +
                ", algo_name='" + algoName + '\'' +
                ", outcome='" + outcome + '\'' +
                ", path=" + path +
                ", time=" + time +
                '}';
    }
}
