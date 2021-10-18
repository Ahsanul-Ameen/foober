package Level_4.Two;
// -----------------------------------------------------------------


import java.util.ArrayList;
import java.util.Arrays;

public class Solution {
    static ArrayList<Integer> saved_list;

    public static int[] solution(int[][] times, int times_limit) {
        // Your code here

        /*
         * Case 1 : reachable -ve cycle exists, then take all bunnies
         * Case 2 : no need to visit any cycle, here many cases may occur
         *          (but in our case we're following a simple path from start to goal (possibly visiting some bunnies))
         */

        int n = times.length; // number of vertices
        int[] saved = new int[]{};

        // simple corner case
        // suppose there is no bunnies to help
        if (n <= 2) {
            return saved;
        }

        boolean cycle_detected = hasNegativeCycle(times);

        if (cycle_detected) {
            saved = new int[n - 2];
            for (int i = 0; i + 2 < n; i++) {
                saved[i] = i;
            }
            return saved;
        }

        // no cycle found
        // now calculate the saved bunnies
        saved_list = new ArrayList<>();
        boolean[] visited = new boolean[n];
        visited[0] = true;
        // try to go to other bunnies and save them
        for (int u = 1; u <= n - 2; ++u) {
            backTrack(u, times_limit - times[0][u], times, visited, new ArrayList<>());
        }

        if (!saved_list.isEmpty()) {
            saved = new int[saved_list.size()];
            for (int i = 0; i < saved.length; ++i)
                saved[i] = saved_list.get(i);

            Arrays.sort(saved);
        }

        return saved;
    }

    private static boolean hasNegativeCycle(int[][] times) {
        int n = times.length;
        // Floyd-Warshall Algorithm
        for (int k = 0; k < n; k++)
            for (int i = 0; i < n; ++i)
                for (int j = 0; j < n; ++j)
                    times[i][j] = Math.min(times[i][j], times[i][k] + times[k][j]);

        // detect the existence of -ve cycle
        for (int i = 0; i < n; ++i)
            if (times[i][i] < 0)
                return true; // -ve cycle found


        return false; // -ve cycle not found
    }

    private static void backTrack(int u, int time_left, int[][] times, boolean[] visited, ArrayList<Integer> list) {
        int n = visited.length;

        // you can't bear the cost (i.e. time is up)
        if (time_left <= -999 || (u == n - 1 && time_left < 0))
            return;

        // in a life-saving state
        if (u == n - 1 && time_left >= 0) { // the gates are still open
            if (list.size() > saved_list.size()) { // found a better solution
                saved_list = new ArrayList<>(list); // we've gathered more bunnies
            }
            return;
        }

        visited[u] = true; // do not come back here again
        list.add(u - 1);   // take the bunny
        for (int v = 1; v < n; ++v) {
            if (!visited[v])
                backTrack(v, time_left - times[u][v], times, visited, list); // go to other bunny or exit
        }
        list.remove(list.size() - 1); // uncheck the bunny
        visited[u] = false; // pretend as u never visited your friend before
    }

    public static void main(String[] args) {

        int[] saved;
        saved = Solution.solution(new int[][]{{0, 1, 1, 1, 1}, {1, 0, 1, 1, 1}, {1, 1, 0, 1, 1}, {1, 1, 1, 0, 1}, {1, 1, 1, 1, 0}}, 3);
        System.out.println(Arrays.toString(saved));
        saved = Solution.solution(new int[][]{{0, 2, 2, 2, -1}, {9, 0, 2, 2, -1}, {9, 3, 0, 2, -1}, {9, 3, 2, 0, -1}, {9, 3, 2, 2, 0}}, 1);
        System.out.println(Arrays.toString(saved));
    }
}
