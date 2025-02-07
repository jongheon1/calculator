package token

import java.math.BigDecimal

sealed interface Operator : Token {
    val symbol: String
    val precedence: Int
    fun calculate(left: BigDecimal, right: BigDecimal): BigDecimal
}

object PlusOperator : Operator {
    override val symbol: String = "+"
    override val precedence: Int = 1
    override fun calculate(left: BigDecimal, right: BigDecimal): BigDecimal =
        left.add(right)
}

object MinusOperator : Operator {
    override val symbol: String = "-"
    override val precedence: Int = 1
    override fun calculate(left: BigDecimal, right: BigDecimal): BigDecimal =
        left.minus(right)
}

object MultiplyOperator : Operator {
    override val symbol: String = "*"
    override val precedence: Int = 2
    override fun calculate(left: BigDecimal, right: BigDecimal): BigDecimal =
        left.multiply(right)
}

object DivideOperator : Operator {
    override val symbol: String = "/"
    override val precedence: Int = 2
    override fun calculate(left: BigDecimal, right: BigDecimal): BigDecimal =
        left.divide(right)
}

object LeftParenthesis : Operator {
    override val symbol: String = "("
    override val precedence: Int = 3
    override fun calculate(left: BigDecimal, right: BigDecimal): BigDecimal =
        throw UnsupportedOperationException("Parenthesis cannot perform calculation")
}

object RightParenthesis : Operator {
    override val symbol: String = ")"
    override val precedence: Int = 3
    override fun calculate(left: BigDecimal, right: BigDecimal): BigDecimal =
        throw UnsupportedOperationException("Parenthesis cannot perform calculation")
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



enum class OperatorEnum(
    val symbol: String,
    val precedence: Int
) : Token {
    PLUS("+", 1),
    MINUS("-", 1);

    fun calculate(left: BigDecimal, right: BigDecimal): BigDecimal =
        when(this) {
            PLUS -> left.add(right)
            MINUS -> left.minus(right)
        }
}