import java.util.Arrays;

class SpaceProbe {
    private static int[][] land;
    private static int landLength;
    private static int landHeight;
    private int x;
    private int y;
    private char facing;

    public SpaceProbe(int x, int y, char facing) {
        isSafe(x, y);
        this.x = x;
        this.y = y;
        this.facing = facing;
        land[x][y] = 1;
    }

    public int[] getPosition() {
        return new int[] {x, y};
    }

    public char getFacing() {
        return facing;
    }

    public void setState(int[] pos, char facing) {
        land[this.x][this.y] = 0;
        land[pos[0]][pos[1]] = 1;
        this.x = pos[0];
        this.y = pos[1];
        this.facing = facing;
    }

    public static void setLand(int x, int y){
        land = new int[x + 1][y + 1];
        landLength = x;
        landHeight = y;
    }

    public static int[][] copyLand() {
        if (land == null) return null;
        int[][] copy = new int[landLength + 1][landHeight + 1];
        for (int i = 0; i <= landLength; i++) {
            copy[i] = Arrays.copyOf(land[i], landHeight + 1);
        }
        return copy;
    }

    public void turnRight() {
        switch (facing) {
            case 'N':
                facing = 'E';
                break;
            case 'E':
                facing = 'S';
                break;
            case 'S':
                facing = 'W';
                break;
            case 'W':
                facing = 'N';
                break;
            default:
                throw new RuntimeException("Facing invalid direction.");
        }
    }

    public void turnLeft() {
        switch (facing) {
            case 'N':
                facing = 'W';
                break;
            case 'W':
                facing = 'S';
                break;
            case 'S':
                facing = 'E';
                break;
            case 'E':
                facing = 'N';
                break;
            default:
                throw new RuntimeException("Facing invalid direction.");
        }
    }

    public void moveForward() {
        int oldX = x;
        int oldY = y;
        switch (facing) {
            case 'N':
                isSafe(x, y+1);
                y++;
                break;
            case 'E':
                isSafe(x+1, y);
                x++;
                break;
            case 'S':
                isSafe(x, y-1);
                y--;
                break;
            case 'W':
                isSafe(x-1, y);
                x--;
                break;
            default:
                throw new RuntimeException("Facing invalid direction.");
        }
        land[oldX][oldY] = 0;
        land[x][y] = 1;
    }

    public static void isSafe(int x, int y) {
        if (x > landLength || x < 0 || y > landHeight || y < 0) {
            throw new RuntimeException("Failed to move, out of the land.");
        } 

        if (land[x][y] != 0) {
            throw new RuntimeException("Failed to move, there's already a space scope in " + x + "," + y);
        }
    }
}