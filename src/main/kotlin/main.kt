import calculator.Calculator
import calculator.Parser
import calculator.PostfixConverter


fun main() {
    val parser = Parser()
    val converter = PostfixConverter()
    val calculator = Calculator()

    println("Calculator started. Type 'exit' to quit.")

    while(true) {
        print("\nEnter expression: ")
        val input = readlnOrNull()?.trim() ?: break

        if (input.isEmpty()) continue
        if (input.equals("exit", ignoreCase = true)) {
            break
        }

        try {
            val tokens = parser.parse(input)
            val postfixTokens = converter.convertToPostfix(tokens)
            val result = calculator.calculate(postfixTokens)
            println("Result: $result")
        } catch (e: IllegalArgumentException) {
            println("Error: ${e.message}")
        } catch (e: Exception) {
            println("Unknown error occurred: ${e.message}")
        }
    }

    println("Calculator terminated.")
}