package token

import java.math.BigDecimal
import java.math.RoundingMode

sealed interface Operator : Token {
    val symbol: String
    val precedence: Int
}

sealed interface BinaryOperator : Operator {
    // 인자를 Operand 로 받고 계산의 실제 구현부는 Operand 에다가 해도 ㄱㅊ을 듯
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
        left.divide(right, 10, RoundingMode.HALF_UP) // 0.333... 끝부분 처리, 1.0000... 자연수 부분만 보이도록 처리 해야 함
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
