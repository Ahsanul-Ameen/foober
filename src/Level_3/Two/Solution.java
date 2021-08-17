package Level_3.Two;

import java.math.BigInteger;

import static java.math.BigInteger.ONE;
import static java.math.BigInteger.ZERO;

public class Solution {

    private static BigInteger calculateSteps(BigInteger M, BigInteger F) {
        BigInteger a = F;
        a = a.max(M);
        BigInteger b = M;
        b = b.min(F);
        BigInteger res;
        // order doesn't matter here
        //System.out.println("calculateSteps(" + a + ", " + b.toString() + ")");

        if (b.compareTo(ONE) == 0) { // (a, 1)
            res = a.add(b.negate()); // (a, 1) -> steps = a - 1 : (1, 1) -> steps = 0
        } else if (b.compareTo(ZERO) == 0) { // (a, 0)
            res = ONE.negate(); // impossible
        } else {
            BigInteger d = a.divide(b); // d = (floor)(a / b)
            a = a.add(b.multiply(d).negate()); // a = a - b * (a / b)
            res = calculateSteps(a, b);
            res = res.compareTo(ONE.negate()) == 0 ? res : res.add(d);
        }

        return res;
    }

    public static String solution(String x, String y) {
        // Your code here
        BigInteger steps = calculateSteps(new BigInteger(x), new BigInteger(y));
        return steps.compareTo(ONE.negate()) == 0 ? "impossible" : steps.toString();
    }

    public static void main(String[] args) {
        String str = Solution.solution("20000000000000000000", "4400000000002");
        System.out.println(str);

        str = Solution.solution("2", "1");
        System.out.println(str);

        str = Solution.solution("4", "7");
        System.out.println(str);

        str = Solution.solution("2", "4");
        System.out.println(str);

        str = Solution.solution("31", "4");
        System.out.println(str);

        str = Solution.solution("32", "4");
        System.out.println(str);
    }
}