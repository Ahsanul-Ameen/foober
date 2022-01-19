package Level_5;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;

public class Solution {
    private static final BigDecimal RTMO = new BigDecimal("2").sqrt(new MathContext(200)).subtract(BigDecimal.ONE);
    public static String solution(String s) {
        BigInteger n = new BigInteger(s);
        if (n.equals(BigInteger.ZERO))
            return n.toString();

        BigInteger np = RTMO.multiply(new BigDecimal(n)).toBigInteger();

        BigInteger term_one = n.multiply(np);
        BigInteger term_two = n.multiply(n.add(BigInteger.ONE)).divide(BigInteger.TWO);
        BigInteger term_three = np.multiply(np.add(BigInteger.ONE)).divide(BigInteger.TWO);

        return term_one.add(term_two.subtract(term_three.add(new BigInteger(solution(np.toString()))))).toString();
        //return term_one.add(term_two).subtract(term_three).subtract(new BigInteger(solution(np.toString()))).toString();
    }

    public static void main(String[] args) {
        System.out.println(RTMO.toString());
        for (String s : new String[]{"5", "77"})
            System.out.println(s + " : " + solution(s));

    }
}
