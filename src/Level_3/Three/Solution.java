package Level_3.Three;


public class Solution {

    public static int solution(int[] l) {
        // Your code here
        int cnt = 0;
        for (int i = 0, n = l.length; i < n - 2; i++) {
            for (int j = i + 1; j < n - 1; j++) {
                if (l[j] % l[i] == 0) {
                    for (int k = j + 1; k < n; k++) {
                        if (l[k] % l[j] == 0)
                            ++cnt;
                    }
                }
            }
        }
        return cnt;
    }

    public static void main(String[] args) {
        System.out.println(Solution.solution(new int[]{1, 1, 1}));
        System.out.println(Solution.solution(new int[]{1, 2, 3, 4, 5, 6}));
    }
}