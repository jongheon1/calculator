package token

import java.math.BigDecimal

sealed interface Operator : Token {
    val symbol: String
    val precedence: Int
}

sealed interface BinaryOperator : Operator {
    fun calculate(left: BigDecimal, right: BigDecimal): BigDecimal
}

object PlusOperator : BinaryOperator {
    override val symbol: String = "+"
    override val precedence: Int = 1
    override fun calculate(left: BigDecimal, right: BigDecimal): BigDecimal =
        left.add(right)
}

object MinusOperator : BinaryOperator {
    override val symbol: String = "-"
    override val precedence: Int = 1
    override fun calculate(left: BigDecimal, right: BigDecimal): BigDecimal =
        left.minus(right)
}

object MultiplyOperator : BinaryOperator {
    override val symbol: String = "*"
    override val precedence: Int = 2
    override fun calculate(left: BigDecimal, right: BigDecimal): BigDecimal =
        left.multiply(right)
}

object DivideOperator : BinaryOperator {
    override val symbol: String = "/"
    override val precedence: Int = 2
    override fun calculate(left: BigDecimal, right: BigDecimal): BigDecimal =
        left.divide(right)
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
        LeftParenthesis.symbol -> LeftParenthesis
        RightParenthesis.symbol -> RightParenthesis
        else -> throw IllegalArgumentException("Unknown operator: $symbol")
    }

}
