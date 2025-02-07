package token

sealed interface Operator : Token {
    val symbol: String
    val precedence: Int
    fun calculate(left: Operand, right: Operand): Double
}

class PlusOperator : Operator {
    override val symbol: String = "+"
    override val precedence: Int = 1
    override fun calculate(left: Operand, right: Operand): Double =
        left.value + right.value
}

class MinusOperator : Operator {
    override val symbol: String = "-"
    override val precedence: Int = 1
    override fun calculate(left: Operand, right: Operand): Double =
        left.value - right.value
}

class MultiplicationOperator : Operator {
    override val symbol: String = "*"
    override val precedence: Int = 2
    override fun calculate(left: Operand, right: Operand): Double =
        left.value * right.value
}

class DivisionOperator : Operator {
    override val symbol: String = "/"
    override val precedence: Int = 2
    override fun calculate(left: Operand, right: Operand): Double =
        left.value / right.value
}

class LeftParenthesis : Operator {
    override val symbol: String = "("
    override val precedence: Int = 3
    override fun calculate(left: Operand, right: Operand): Double =
        throw UnsupportedOperationException("Parenthesis cannot perform calculation")
}

class RightParenthesis : Operator {
    override val symbol: String = ")"
    override val precedence: Int = 3
    override fun calculate(left: Operand, right: Operand): Double =
        throw UnsupportedOperationException("Parenthesis cannot perform calculation")
}



enum class OperatorEnum(
    val symbol: String,
    val precedence: Int
) : Token {
    PLUS("+", 1),
    MINUS("-", 1);

    fun calculate(left: Operand, right: Operand): Double =
        when(this) {
            PLUS -> left.value + right.value
            MINUS -> left.value - right.value
        }
}