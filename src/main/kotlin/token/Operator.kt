package token

import java.math.BigDecimal
import java.math.RoundingMode

sealed interface Operator : Token {
    val symbol: String
    val precedence: Int
}

sealed interface BinaryOperator : Operator {
    fun calculate(left: Operand, right: Operand): Operand
}

object PlusOperator : BinaryOperator {
    override val symbol: String = "+"
    override val precedence: Int = 1
    override fun calculate(left: Operand, right: Operand): Operand =
        left.add(right)
}

object MinusOperator : BinaryOperator {
    override val symbol: String = "-"
    override val precedence: Int = 1
    override fun calculate(left: Operand, right: Operand): Operand =
        left.subtract(right)
}

object MultiplyOperator : BinaryOperator {
    override val symbol: String = "*"
    override val precedence: Int = 2
    override fun calculate(left: Operand, right: Operand): Operand =
        left.multiply(right)
}

object DivideOperator : BinaryOperator {
    override val symbol: String = "/"
    override val precedence: Int = 2
    override fun calculate(left: Operand, right: Operand): Operand =
        left.divide(right)
}

object ModOperator : BinaryOperator {
    override val symbol: String = "%"
    override val precedence: Int = 2
    override fun calculate(left: Operand, right: Operand): Operand =
        left.mod(right)
}

object PowerOperator : BinaryOperator {
    override val symbol: String = "^"
    override val precedence: Int = 3
    override fun calculate(left: Operand, right: Operand): Operand =
        left.pow(right)
}


sealed interface Parenthesis : Operator

object LeftParenthesis : Parenthesis {
    override val symbol: String = "("
    override val precedence: Int = 3
}

object RightParenthesis : Parenthesis {
    override val symbol: String = ")"
    override val precedence: Int = 3
}


object OperatorFactory {

    fun fromSymbol(symbol: String): Operator = when(symbol) {
        PlusOperator.symbol -> PlusOperator
        MinusOperator.symbol -> MinusOperator
        MultiplyOperator.symbol -> MultiplyOperator
        DivideOperator.symbol -> DivideOperator
        ModOperator.symbol -> ModOperator
        PowerOperator.symbol -> PowerOperator
        LeftParenthesis.symbol -> LeftParenthesis
        RightParenthesis.symbol -> RightParenthesis
        else -> throw IllegalArgumentException("Unknown operator: $symbol")
    }

}
