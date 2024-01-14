/**
 * @author Święch Aleksander S29379
 */

package zad1;


import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

public class Calc {
    private static final Map<String, BiFunction<BigDecimal, BigDecimal, BigDecimal>> OPERATIONS = new HashMap<>();

    static {
        OPERATIONS.put("+", BigDecimal::add);
        OPERATIONS.put("-", BigDecimal::subtract);
        OPERATIONS.put("*", BigDecimal::multiply);
        OPERATIONS.put("/", (a, b) -> a.divide(b, 7, RoundingMode.HALF_UP));
    }

    public String doCalc(String cmd) {
        try {
            String[] parts = cmd.split("\\s+");

            validateLength(parts); // if length is not 3, throws exception

            // throws exception if number format is invalid
            BigDecimal operand1 = new BigDecimal(parts[0]);
            BigDecimal operand2 = new BigDecimal(parts[2]);

            // throws exception if operator is invalid
            BiFunction<BigDecimal, BigDecimal, BigDecimal> operation = getOperation(parts[1]);
            BigDecimal result = operation.apply(operand1, operand2);
            return result.stripTrailingZeros().toPlainString();
        } catch (ArithmeticException e) {
            return "Invalid command to calc. Provide valid operator and operands separated by spaces.";
        } catch (NullPointerException e){
            return "Invalid operator. Valid operators are: + - * /";
        } catch (NumberFormatException e) {
            return "Invalid number format. Valid number format is: 0.0";
        }
    }

    private void validateLength(String[] parts) throws ArithmeticException {
        int crash = 1 / (parts.length - 3);
    }

    private BiFunction<BigDecimal, BigDecimal, BigDecimal> getOperation(String operator)
            throws NullPointerException {
        BiFunction<BigDecimal, BigDecimal, BigDecimal> operation = OPERATIONS.get(operator);
        int crash = operation.hashCode();
        return operation;
    }
}