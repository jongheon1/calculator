package calculator

import token.Operand
import token.OperatorFactory
import token.Token
import java.math.BigDecimal

class Parser {

    fun parse(input: String): List<Token> {
        val tokens = mutableListOf<Token>()
        val operandBuilder = StringBuilder()

        fun addOperandIfPresent() {
            if (operandBuilder.isNotEmpty()) {
                val operand = Operand(BigDecimal(operandBuilder.toString()))
                tokens.add(operand)
                operandBuilder.clear()
            }
        }

        input.forEach { char ->
            when {
                char.isDigit() || char == '.' -> operandBuilder.append(char)
                char.isWhitespace() -> addOperandIfPresent()
                char == '-' -> {
                    
                }
                else -> {
                    addOperandIfPresent()
                    val operator = OperatorFactory.fromSymbol(char.toString())
                    tokens.add(operator)
                }
            }
        }


    }
}

