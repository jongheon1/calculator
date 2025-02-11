import calculator.Calculator
import calculator.Parser
import calculator.PostfixConverter
import token.Operand


fun main() {
    val parser = Parser()
    val converter = PostfixConverter()
    val calculator = Calculator()

    fun process(input: String): Operand {
        val tokens = parser.parse(input)
        val postfixTokens = converter.convertToPostfix(tokens)
        return calculator.calculate(postfixTokens)
    }

    println("Calculator started. Type 'exit' to quit.")

    while(true) {
        print("\nEnter expression: ")
        val input = readlnOrNull()?.trim() ?: break

        if (input.isEmpty()) continue
        if (input.equals("exit", ignoreCase = true)) {
            break
        }

        try {
            val result = process(input)
            println("Result: $result")
        } catch (e: IllegalArgumentException) {
            println("Error: ${e.message}")
        } catch (e: Exception) {
            println("Unknown error occurred: ${e.message}")
        }
    }

    println("Calculator terminated.")
}