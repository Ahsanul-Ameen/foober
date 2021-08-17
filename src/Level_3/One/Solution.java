package Level_3.One;

import java.util.LinkedList;
import java.util.Queue;


public class Solution {

    static int[] dirX = {+1, -1, 0, 0};
    static int[] dirY = {0, 0, +1, -1};

    public static int steps(int[][] map) {
        // direction array
        int H = map.length;
        int W = map[0].length;

        boolean[][] visited = new boolean[H][W];

        int moves = 1;
        Queue<Integer> qx = new LinkedList<>();
        Queue<Integer> qy = new LinkedList<>();
        qx.offer(0);
        qy.offer(0);
        visited[0][0] = true;
        // add layer end flag
        qx.offer(-1);
        qy.offer(-1);

        while(!qx.isEmpty()) {
            // pull out the cell
            int x = qx.remove();
            int y = qy.remove();
            if(x == -1 && y == -1) { // layer change
                ++moves;
                if (!qx.isEmpty()) {  // add layer end flag only if there exists a new layer
                    qx.offer(-1);
                    qy.offer(-1);
                }
                continue;
            }

            if (x + 1 == H && y + 1 == W)
                return moves;

            for(int i = 0; i < 4; i++) {
                int nx = x + dirX[i];
                int ny = y + dirY[i];

                if (nx < 0 || ny < 0 || nx >= H || ny >= W
                        || visited[nx][ny] || map[nx][ny] == 1)
                    continue;

                qx.offer(nx);
                qy.offer(ny);
                visited[nx][ny] = true;
            }
        }

        return Integer.MAX_VALUE;
    }

    public static int solution(int[][] map) {
        // Your code here
        int H = map.length;
        int W = map[0].length;

        int result = steps(map);
        for(int i = 0; i < H; i++) {
            for(int j = 0; j < W; j++) {
                if (map[i][j] == 1) {
                    map[i][j] = 0;
                    result = Math.min(result, steps(map));
                    map[i][j] = 1;
                }
            }
        }

        return result;
    }

    public static void main(String[] args) {
        int steps = Solution.solution(new int[][]
                {{0, 0, 0, 0, 0, 0}, {1, 1, 1, 1, 1, 0}, {0, 0, 0, 0, 0, 0}, {0, 1, 1, 1, 1, 1}, {0, 1, 1, 1, 1, 1}, {0, 0, 0, 0, 0, 0}}
        );

        System.out.println(steps);
    }
}