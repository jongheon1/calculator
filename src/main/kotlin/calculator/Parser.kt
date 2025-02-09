package calculator

import token.*
import java.math.BigDecimal

class Parser {

    fun parse(input: String): List<Token> {
        val tokens = mutableListOf<Token>()
        val operandBuilder = StringBuilder()

        fun addOperandIfPresent() {
            if (operandBuilder.isNotEmpty()) {
                try {
                    val operand = Operand(BigDecimal(operandBuilder.toString()))
                    tokens.add(operand)
                    operandBuilder.clear()
                } catch (e: NumberFormatException) {
                    throw IllegalArgumentException("Invalid number format in expression: $operandBuilder", e)
                }
            }
        }

        input.forEach { char ->
            when {
                char.isDigit() || char == '.' -> operandBuilder.append(char)
                char.isWhitespace() -> addOperandIfPresent()
                char == '-' -> {
                    addOperandIfPresent()

                    if (tokens.isEmpty() ||
                        (tokens.last() is Operator && tokens.last() !is RightParenthesis)) {
                        operandBuilder.append(char)
                    } else {
                        tokens.add(MinusOperator)
                    }
                }
                else -> {
                    addOperandIfPresent()
                    val operator = OperatorFactory.fromSymbol(char.toString())
                    tokens.add(operator)
                }
            }
        }
        addOperandIfPresent()

        return tokens
    }
}

