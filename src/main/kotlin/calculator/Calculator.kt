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
                    val right = stack.removeLast()
                    val left = stack.removeLast()
                    val result = try {
                        token.calculate(left, right)
                    } catch (e: ArithmeticException) {
                        throw IllegalArgumentException("Arithmetic error: ${e.message}")
                    }
                    stack.add(result)
                }
                is Parenthesis -> throw IllegalArgumentException("Unexpected parenthesis in calculation")
            }
        }

        check(stack.size == 1)
        return stack.last().value
    }
}