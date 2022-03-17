import java.util.Scanner;

public class Input {
    public Point harry;
    public Point filch;
    public Point cat;
    public Point book;
    public Point cloak;
    public Point exit;
    public int scenario;

    public void readFromStdin() throws Exception {
        Scanner sc = new Scanner(System.in);
        harry = getCoordsFromString(sc.next());
        filch = getCoordsFromString(sc.next());
        cat = getCoordsFromString(sc.next());
        book = getCoordsFromString(sc.next());
        cloak = getCoordsFromString(sc.next());
        exit = getCoordsFromString(sc.next());
        scenario = sc.nextInt();
    }

    public void readFromFile() {
    }

    public void generate() {
    }

    private Point getCoordsFromString(String s) throws Exception {
        // TODO: Rewrite to regexp
        String[] splitted = s.replace("[", "").replace("]", "").split(",");
        if (splitted.length != 2) throw new Exception("Check the input format");
        return new Point(Integer.parseInt(splitted[0]), Integer.parseInt(splitted[1]));
    }
}
