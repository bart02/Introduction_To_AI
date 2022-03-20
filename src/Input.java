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

    public void generate() throws Exception {
        Environment env;
        while (true) {
            env = new Environment(9);
            harry = new Point(0, 0);
            env.setHarry(harry, 1);

            try {
                filch = Point.generate();
                env.setFilch(filch);
            } catch (Exception ignored) {
                continue;
            }

            try {
                cat = Point.generate();
                env.setFilch(cat);
            } catch (Exception ignored) {
                continue;
            }

            try {
                book = Point.generate();
                env.setFilch(book);
            } catch (Exception ignored) {
                continue;
            }

            try {
                cloak = Point.generate();
                env.setFilch(cloak);
            } catch (Exception ignored) {
                continue;
            }

            try {
                exit = Point.generate();
                env.setFilch(exit);
            } catch (Exception ignored) {
                continue;
            }
            break;
        }
        scenario = 2;
    }

    private Point getCoordsFromString(String s) throws Exception {
        // TODO: Rewrite to regexp
        String[] splitted = s.replace("[", "").replace("]", "").split(",");
        if (splitted.length != 2) throw new Exception("Check the input format");
        return new Point(Integer.parseInt(splitted[0]), Integer.parseInt(splitted[1]));
    }

    @Override
    public String toString() {
        return "Input{" +
                "harry=" + harry +
                ", filch=" + filch +
                ", cat=" + cat +
                ", book=" + book +
                ", cloak=" + cloak +
                ", exit=" + exit +
                ", scenario=" + scenario +
                '}';
    }
}
