import java.util.Scanner;

public class SpaceManager {

    private static int[][] land;
    private static int landLength;
    private static int landHeight;
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        landLength = Integer.parseInt(in.next());
        landHeight = Integer.parseInt(in.next());
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
                    if (ex.getMessage().startsWith("Failed to move, there's already a space scope in")) {
                        int jumpToInd = findNextPos(commands, i, probe.getPosition(), probe.getFacing(), probe);
                        if (jumpToInd == -1) {
                            System.out.print(ex.getMessage() + " and there's no way to surpass it.");
                            break;
                        }
                        i = jumpToInd;
                        continue;
                    }
                    if (ex.getMessage() == "Failed to move, out of the land.") {
                        System.out.print(ex.getMessage());
                        break;
                    }
                    throw ex;
                }
            }

            int[] probePos = probe.getPosition();
            System.out.println(probePos[0] + " " + probePos[1] + " " + probe.getFacing());
        }

        in.close();
    }

    private static int findNextPos(String currentCommands, int ind, int[] currentPos, char currentFacing, SpaceProbe probe) {
        for (int i = ind + 1; i < currentCommands.length(); i++) {
            if (currentCommands.charAt(i) == 'M') {
                switch (currentFacing) {
                    case 'N':
                        currentPos[1]++;
                        break;
                    case 'E':
                        currentPos[0]++;
                        break;
                    case 'S':
                        currentPos[1]--;
                        break;
                    case 'W':
                        currentPos[0]--;
                        break;
                    default:
                        throw new RuntimeException("Facing invalid direction.");
                }
                try {
                    SpaceProbe.isSafe(currentPos[0], currentPos[1]);
                } catch (Exception ex) {
                    if (ex.getMessage() == "Failed to move, out of the land.") {
                        return -1;
                    }
                    if (ex.getMessage().startsWith("Failed to move, there's already a space scope in")) {
                        return findNextPos(currentCommands, i, currentPos, currentFacing, probe);
                    }
                    throw ex;
                }
                if (isThereAnotherPathTo(currentPos, probe)) {
                    probe.setState(currentPos, currentFacing);
                    return i;
                }
                return -1;
            }

            else if (currentCommands.charAt(i) == 'R') {
                switch (currentFacing) {
                    case 'N':
                        currentFacing = 'E';
                        break;
                    case 'E':
                        currentFacing = 'S';
                        break;
                    case 'S':
                        currentFacing = 'W';
                        break;
                    case 'W':
                        currentFacing = 'N';
                        break;
                    default:
                        throw new RuntimeException("Facing invalid direction.");
                }
            }
            else {
                switch (currentFacing) {
                    case 'N':
                        currentFacing = 'W';
                        break;
                    case 'W':
                        currentFacing = 'S';
                        break;
                    case 'S':
                        currentFacing = 'E';
                        break;
                    case 'E':
                        currentFacing = 'N';
                        break;
                    default:
                        throw new RuntimeException("Facing invalid direction.");
                }
            }
        }
        return -1;
    }

    private static boolean isThereAnotherPathTo(int[] pos, SpaceProbe probe) {
        land = SpaceProbe.getLand();
        int[] currentPos = probe.getPosition();
        return (findPath(new int[] {currentPos[0]+1, currentPos[1]},  pos) ||
            findPath(new int[] {currentPos[0]-1, currentPos[1]},  pos) ||
            findPath(new int[] {currentPos[0], currentPos[1]+1},  pos) ||
            findPath(new int[] {currentPos[0], currentPos[1]-1},  pos)
        );
    }

    private static boolean findPath(int[] from, int[] to) {
        if (from[0] == to[0] && from[1] == to[1]) {
            return true;
        }
        if (from[0] < 0 || from[0] > landLength ||
            from[1] < 0 || from[1] > landHeight ||
            land[from[0]][from[1]] != 0) {
                return false;
        }
        land[from[0]][from[1]] = -1;
        return (findPath(new int[] { from[0]+1, from[1] }, to) ||
            findPath(new int[] { from[0]-1, from[1] }, to) ||
            findPath(new int[] { from[0], from[1]+1 }, to) ||
            findPath(new int[] { from[0], from[1]-1 }, to)
        );
    }
}
