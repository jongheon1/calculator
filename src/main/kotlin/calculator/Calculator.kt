package calculator

import token.*
import java.math.BigDecimal

class Calculator {
    fun calculate(tokens: List<Token>): BigDecimal {

        val stack = ArrayDeque<Operand>()

        tokens.forEach { token ->
            when (token) {
                is Operand -> stack.add(token)
                is BinaryOperator -> {
//                    check(stack.size >= 2) { "Unexpected state: insufficient operands" }
                    val right = stack.removeLast()
                    val left = stack.removeLast()
                    val result = try {
                        token.calculate(
                            left = left.value,
                            right = right.value
                        )
                    } catch (e: ArithmeticException) {
                        throw IllegalArgumentException("Arithmetic error: ${e.message}")
                    }
                    stack.add(Operand(value = result))
                }
                is Parenthesis -> throw IllegalArgumentException("Unexpected parenthesis in calculation")
            }
        }

        check(stack.size == 1)
        return stack.last().value
    }
}