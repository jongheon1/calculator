package calculator

import token.*

class PostfixConverter {

    fun convertToPostfix(tokens: List<Token>): List<Token> {
        validateExpression(tokens)

        val result = mutableListOf<Token>()
        val stack = ArrayDeque<Operator>()

        tokens.forEach { token ->
            when(token) {
                is Operand -> result.add(token)
                is Operator -> {
                    when(token) {
                        is LeftParenthesis -> stack.add(token)
                        is RightParenthesis -> {
                            while (stack.isNotEmpty() &&
                                stack.last() !is LeftParenthesis) {
                                result.add(stack.removeLast())
                            }
                            stack.removeLast()
                        }
                        else -> {
                            while (stack.isNotEmpty() &&
                                stack.last() !is LeftParenthesis &&
                                stack.last().precedence >= token.precedence) {
                                result.add(stack.removeLast())
                            }
                            stack.add(token)
                        }
                    }
                }
            }
        }

        while (stack.isNotEmpty()) {
            result.add(stack.removeLast())
        }

        return result
    }

    private fun validateExpression(tokens: List<Token>) {
        if (tokens.isEmpty()) {
            throw IllegalArgumentException("Expression is empty")
        }

        var parenthesesCount = 0

        var prevToken: Token? = null

        tokens.forEachIndexed { index, token ->
            when(token) {
                is LeftParenthesis -> {
                    parenthesesCount++
                    if (prevToken is Operand) {
                        throw IllegalArgumentException("Invalid operand before opening parenthesis")
                    }
                }
                is RightParenthesis -> {
                    parenthesesCount--
                    if (parenthesesCount < 0) {
                        throw IllegalArgumentException("Unmatched closing parenthesis")
                    }
                    if (prevToken is LeftParenthesis) {
                        throw IllegalArgumentException("Empty parentheses")
                    }
                    if (prevToken is BinaryOperator) {
                        throw IllegalArgumentException("Invalid operator before closing parenthesis")
                    }
                }
                is BinaryOperator -> {
                    if (index == 0) {
                        throw IllegalArgumentException("Invalid expression: starts with operator")
                    }
                    if (index == tokens.lastIndex) {
                        throw IllegalArgumentException("Invalid expression: ends with operator")
                    }
                    if (prevToken is BinaryOperator) {
                        throw IllegalArgumentException("Consecutive operators")
                    }
                    if (prevToken is LeftParenthesis) {
                        throw IllegalArgumentException("Invalid operator after opening parenthesis")
                    }
                }
                is Operand ->   {
                    if (prevToken is Operand) {
                        throw IllegalArgumentException("Consecutive operands")
                    }
                }
            }
            prevToken = token
        }

        if (parenthesesCount > 0) {
            throw IllegalArgumentException("Unmatched opening parenthesis")
        }
    }
}