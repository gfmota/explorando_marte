import java.util.Scanner;

public class SpaceManager {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int landLength = Integer.parseInt(in.next());
        int landHeight = Integer.parseInt(in.next());
        SpaceProbe.setLand(landLength, landHeight);
        in.nextLine();

        while (in.hasNext()) {
            String[] line = in.nextLine().split(" ");
            SpaceProbe probe;
            try {
                probe = new SpaceProbe(
                    Integer.parseInt(line[0]),
                    Integer.parseInt(line[1]),
                    line[2].charAt(0)
                );
            } catch (Exception ex) {
                if (ex.getMessage() == "Failed to move, out of the land.") {
                    System.out.println("Invalid position input: out of the land.");
                    break;
                }
                if (ex.getMessage().startsWith("Failed to move, there's already a space scope in ")) {
                    System.out.println("Invalid position input: there's already a space probe in this position.");
                    break;
                }
                throw ex;
            }

            String commands = in.nextLine();
            for (int i = 0; i < commands.length(); i++) {
                try {
                    switch (commands.charAt(i)) {
                        case 'M':
                            probe.moveForward();
                            break;
                        case 'R':
                            probe.turnRight();
                            break;
                        case 'L':
                            probe.turnLeft();
                            break;
                        default:
                            throw new RuntimeException("Invalid input.");
                    }
                } catch (RuntimeException ex) {
                    if (ex.getMessage() == "Facing invalid direction.") {
                        System.out.println("Invalid direction input");
                        break;
                    }
                    if (ex.getMessage() == "Failed to move, out of the land.") {
                        System.out.print(ex.getMessage());
                        break;
                    }
                    throw ex;
                }
            }

            probe.printPosition();
        }

        in.close();
    }
}
