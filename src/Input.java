import java.util.Random;
import java.util.Scanner;

public class Input {
    public Point harry;
    public Point filch;
    public Point cat;
    public Point book;
    public Point cloak;
    public Point exit;
    public int scenario;
    Scanner scanner;

    public Input() {
        scanner = new Scanner(System.in);
    }

    public void readFromStdin() throws InputException {
        System.out.println("Please write input data (2 strings):");
        harry = getCoordsFromString(scanner.next());
        filch = getCoordsFromString(scanner.next());
        cat = getCoordsFromString(scanner.next());
        book = getCoordsFromString(scanner.next());
        cloak = getCoordsFromString(scanner.next());
        exit = getCoordsFromString(scanner.next());
        scenario = scanner.nextInt();
    }

    public void generate(int s) {
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

        Random random = new Random();
        if (s == 1 || s == 2) scenario = s;
        else scenario = random.nextInt(2) + 1;

        System.out.println(this);
        System.out.println();
    }

    public void generate() {
        generate(-1);
    }

    public void interactive() throws InputException {
        System.out.print("Write G for using generated input (default), or I for reading from stdin: ");
        switch (scanner.nextLine()) {
            case "I":
                readFromStdin();
                break;
            case "G":
            case "":
                generate();
                break;
            default:
                throw new InputException("Please, use only G, I or empty");
        }
    }

    private Point getCoordsFromString(String s) throws InputException {
        // TODO: Rewrite to regexp
        String[] splitted = s.replace("[", "").replace("]", "").split(",");
        if (splitted.length != 2) throw new InputException("Check the input format");
        return new Point(Integer.parseInt(splitted[0]), Integer.parseInt(splitted[1]));
    }

    @Override
    public String toString() {
        return harry + " " + filch + " " + cat + " " + book + " " + cloak + " " + exit + "\n" + scenario;
    }
}
