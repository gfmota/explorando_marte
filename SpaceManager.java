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
            SpaceProbe probe = new SpaceProbe(
                Integer.parseInt(line[0]),
                Integer.parseInt(line[1]),
                line[2].charAt(0)
            );

            String commands = in.nextLine();
            for (int i = 0; i < commands.length(); i++) {
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
            }

            probe.printPosition();
        }
    }
}
