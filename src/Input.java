import java.util.Scanner;

public class Input {
    public Point harry;
    public Point filch;
    public Point cat;
    public Point book;
    public Point cloak;
    public Point exit;
    public int scenario;

    public void readFromStdin() throws InputException {
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
        Environment env;
        while (true) {
            env = new Environment(9);

            try {
                harry = new Point(0, 0);
                env.setHarry(harry, 1);
            } catch (Exception ignored) {
                continue;
            }

            try {
                filch = Point.generate();
                env.setFilch(filch);
            } catch (Exception ignored) {
                continue;
            }

            try {
                cat = Point.generate();
                env.setCat(cat);
            } catch (Exception ignored) {
                continue;
            }

            try {
                book = Point.generate();
                env.setBook(book);
            } catch (Exception ignored) {
                continue;
            }

            try {
                cloak = Point.generate();
                env.setCloak(cloak);
            } catch (Exception ignored) {
                continue;
            }

            try {
                exit = Point.generate();
                env.setExit(exit);
            } catch (Exception ignored) {
                continue;
            }
            break;
        }
        scenario = 1;
    }

    private Point getCoordsFromString(String s) throws InputException {
        // TODO: Rewrite to regexp
        String[] splitted = s.replace("[", "").replace("]", "").split(",");
        if (splitted.length != 2) throw new InputException("Check the input format");
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
