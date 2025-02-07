package token

import java.math.BigDecimal

sealed interface Operator : Token {
    val symbol: String
    val precedence: Int
    fun calculate(left: BigDecimal, right: BigDecimal): BigDecimal
}

class PlusOperator : Operator {
    override val symbol: String = "+"
    override val precedence: Int = 1
    override fun calculate(left: BigDecimal, right: BigDecimal): BigDecimal =
        left.add(right)
}

class MinusOperator : Operator {
    override val symbol: String = "-"
    override val precedence: Int = 1
    override fun calculate(left: BigDecimal, right: BigDecimal): BigDecimal =
        left.minus(right)
}

class MultiplicationOperator : Operator {
    override val symbol: String = "*"
    override val precedence: Int = 2
    override fun calculate(left: BigDecimal, right: BigDecimal): BigDecimal =
        left.multiply(right)
}

class DivisionOperator : Operator {
    override val symbol: String = "/"
    override val precedence: Int = 2
    override fun calculate(left: BigDecimal, right: BigDecimal): BigDecimal =
        left.divide(right)
}

class LeftParenthesis : Operator {
    override val symbol: String = "("
    override val precedence: Int = 3
    override fun calculate(left: BigDecimal, right: BigDecimal): BigDecimal =
        throw UnsupportedOperationException("Parenthesis cannot perform calculation")
}

class RightParenthesis : Operator {
    override val symbol: String = ")"
    override val precedence: Int = 3
    override fun calculate(left: BigDecimal, right: BigDecimal): BigDecimal =
        throw UnsupportedOperationException("Parenthesis cannot perform calculation")
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