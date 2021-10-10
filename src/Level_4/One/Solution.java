package Level_4.One;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;

public class Solution {

    private static int nCr(int n, int r) {
        r = Math.min(r, n - r); // n C r = n C n-r
        /*
         * nCr = n! / (r! * (n - r)!)
         *     = n * (n - 1) * ... * (n - r + 1) * (n - r)! / (r! * (n - r)!)
         *     = n * (n - 1) * ... * (n - r + 1) / r!
         */
        int res = 1;
        for (int i = r; i >= 1; --i) {
            res *= (n - r + i);
            res /= (r - i + 1);
        }

        return res;
    }

    static int indx = 0; // used in distributedKeys
    private static void distributeKeys(int n, int r, ArrayList<Integer> list, int[][] arr) {
        if (indx >= arr.length) {
            return;
        }
        if (list.size() == r) {
            for (int i = 0; i < list.size(); ++i) {
                arr[indx][i] = list.get(i);
            }
            indx++;
            return;
        }

        int next = list.isEmpty()  ? 1 : (list.get(list.size() - 1) + 1);

        if (n - next + 1 < r - list.size())
            return; // not much item left to take and complete r items

        for (; next <= n; ++next) {
            // take next
            list.add(next);
            distributeKeys(n, r,list, arr);
            // undo
            list.remove(list.size() - 1);
        }
    }

    public static int[][] solution(int num_buns, int num_required) {
        // Your code here

        // num_keys = num_locks
        int num_keys = nCr(num_buns, num_required - 1);

        // a chosen bunny has to carry that many supportive distinct keys
        int keys_per_bunny = nCr(num_buns - 1, num_required - 1);

        // the other buns have to carry the same key chosen for any (num_required - 1) subset of buns
        // thus each key belongs to buns_per_key bunnies
        int buns_per_key = num_buns - (num_required - 1);

        /*
        * Hint: total number of keys is equal to number of ways to choose buns_per_key from num_buns
        * as nCr(num_buns, num_required - 1) = nCr(num_buns, num_buns - num_required + 1)
        *
        * Now we'll generate all possible combinations of nCr(num_buns, buns_per_key)
        * and assign each of the keys to one of the remaining combination
        */

        int[][] arr = new int[num_keys][buns_per_key];
        ArrayList<Integer> list = new ArrayList<>(buns_per_key);
        distributeKeys(num_buns, buns_per_key, list, arr);
        indx = 0; // reset for consecutive calls
        //printArray(arr);

        int[][] buns_and_keys = new int[num_buns][keys_per_bunny];
        int[] indices = new int[num_buns]; // bookkeeping indices of keys for each bunny

        for (int k = 0; k < num_keys; ++k) {
            for (int b = 0; b < buns_per_key; ++b) {
                int bunny = arr[k][b];
                buns_and_keys[bunny - 1][indices[bunny - 1]++] = k;
            }
        }

        // sort lexicographically
        for (int b = 0; b < num_buns; ++b)
            Arrays.sort(buns_and_keys[b]);
        Arrays.sort(buns_and_keys, Comparator.comparingInt(a -> a[0]));


        return buns_and_keys;
    }

    public static void main(String[] args) {

        int[][] arr = Solution.solution(5, 3);
        printArray(arr);

        arr = Solution.solution(2, 1);
        printArray(arr);

        arr = Solution.solution(4, 4);
        printArray(arr);

    }

    private static void printArray(int[][] arr) {
        for (int[] ints : arr) {
            for (int anInt : ints) {
                System.out.print(anInt + " ");
            }
            System.out.println();
        }
        System.out.println();
    }
}