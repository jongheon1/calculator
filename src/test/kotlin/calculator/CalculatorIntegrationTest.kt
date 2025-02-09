import calculator.Calculator
import calculator.Parser
import calculator.PostfixConverter
import org.junit.jupiter.api.assertThrows
import java.math.BigDecimal
import kotlin.test.Test
import kotlin.test.assertEquals

class CalculatorIntegrationTest {
    private val parser = Parser()
    private val converter = PostfixConverter()
    private val calculator = Calculator()

    private fun processExpression(input: String): BigDecimal {
        val tokens = parser.parse(input)
        val postfixTokens = converter.convertToPostfix(tokens)
        return calculator.calculate(postfixTokens)
    }

    @Test
    fun `기본 사칙연산`() {
        assertEquals(
            expected = BigDecimal("3"),
            actual = processExpression("1 + 2")
        )
        assertEquals(
            expected =  BigDecimal("-1"),
            actual =  processExpression("1 - 2")
        )
        assertEquals(
            expected = BigDecimal("6"),
            actual =  processExpression("2 * 3")
        )
        assertEquals(
            expected =  BigDecimal("2"),
            actual =  processExpression("4 / 2")
        )
    }

    @Test
    fun `복잡한 수식 계산`() {
        // 1 + 2 * 3 = 7
        assertEquals(
            expected = BigDecimal("7"),
            actual = processExpression("1 + 2 * 3")
        )

        // (1 + 2) * 3 = 9
        assertEquals(
            expected = BigDecimal("9"),
            actual = processExpression("(1 + 2) * 3")
        )

        // ((1 + 2) * (3 + 4)) = 21
        assertEquals(
            expected = BigDecimal("21"),
            actual = processExpression("((1 + 2) * (3 + 4))")
        )
    }

    @Test
    fun `여러 연산자가 혼합된 수식`() {
        assertEquals(
            expected = BigDecimal("5"),
            actual = processExpression("1 + 2 * 3 - 4 / 2")
        )

        assertEquals(
            expected = BigDecimal("7"),
            actual = processExpression("(1 + 2) * 3 - 4 / (1 + 1)")
        )

        assertEquals(
            expected = BigDecimal("-3"),
            actual = processExpression("(-1 * 2) + 3 + -4 / (1 * 1)")
        )
    }

    @Test
    fun `음수를 포함한 수식`() {
        assertEquals(
            expected = BigDecimal("-1"),
            actual = processExpression("-1")
        )
        assertEquals(
            expected = BigDecimal("1"),
            actual = processExpression("2 + -1")
        )
        assertEquals(
            expected = BigDecimal("-6"),
            actual = processExpression("-2 * 3")
        )
        assertEquals(
            expected = BigDecimal("-1"),
            actual = processExpression("-2 + 1")
        )
        assertEquals(
            expected = BigDecimal("-5"),
            actual = processExpression("(-2 - 3)")
        )
    }

    @Test
    fun `소수점이 포함된 수식`() {
        assertEquals(
            expected = BigDecimal("3.5"),
            actual = processExpression("1.5 + 2")
        )
        assertEquals(
            expected = BigDecimal("3.75"),
            actual = processExpression("1.5 * 2.5")
        )
        assertEquals(
            expected = BigDecimal("0.5"),
            actual = processExpression("2.5 - 2")
        )
        assertEquals(
            expected = BigDecimal("0.5"),
            actual = processExpression("1 / 2")
        )
    }

    @Test
    fun `공백이 다양하게 포함된 수식`() {
        assertEquals(
            expected = BigDecimal("3"),
            actual = processExpression("1+2")
        )
        assertEquals(
            expected = BigDecimal("3"),
            actual = processExpression("1 + 2")
        )
        assertEquals(
            expected = BigDecimal("3"),
            actual = processExpression(" 1 + 2 ")
        )
        assertEquals(
            expected = BigDecimal("3"),
            actual = processExpression("1    +    2")
        )
    }

    @Test
    fun `중첩된 복잡한 괄호 수식`() {
        // (1 + (2 + 3) * (4 + 5)) = 46
        assertEquals(
            expected = BigDecimal("46"),
            actual = processExpression("(1 + (2 + 3) * (4 + 5))")
        )

        // ((1 + 2) * 3 + (4 * 5)) = 29
        assertEquals(
            expected = BigDecimal("29"),
            actual = processExpression("((1 + 2) * 3 + (4 * 5))")
        )
    }

    @Test
    fun `큰 숫자의 연산`() {
        assertEquals(
            expected = BigDecimal("99999980000001"),
            actual = processExpression("9999999 * 9999999")
        )
    }

    @Test
    fun `잘못된 입력에 대한 예외 처리`() {
        // 잘못된 문자가 포함된 경우
        assertThrows<IllegalArgumentException> {
            processExpression("1 + a")
        }

        // 연산자로 시작하는 경우
        assertThrows<IllegalArgumentException> {
            processExpression("+ 1 2")
        }

        // 연산자로 끝나는 경우
        assertThrows<IllegalArgumentException> {
            processExpression("1 + 2 +")
        }

        // 연속된 연산자
        assertThrows<IllegalArgumentException> {
            processExpression("1 + + 2")
        }

        // 괄호가 맞지 않는 경우
        assertThrows<IllegalArgumentException> {
            processExpression("(1 + 2")
        }
        assertThrows<IllegalArgumentException> {
            processExpression("1 + 2)")
        }

        // 빈 괄호
        assertThrows<IllegalArgumentException> {
            processExpression("1 + ()")
        }

        // 괄호 앞에 피연산자
        assertThrows<IllegalArgumentException> {
            processExpression("1 +  1(1+1)")
        }

        // 괄호 앞에 연산자
        assertThrows<IllegalArgumentException> {
            processExpression("(1+)")
        }

        // 연산자 앞에 괄호
        assertThrows<IllegalArgumentException> {
            processExpression("(+1)")
        }

        // 0으로 나누기
        assertThrows<IllegalArgumentException> {
            processExpression("1 / 0")
        }

        // 소수점이 연속으로 있는 경우
        assertThrows<IllegalArgumentException> {
            processExpression("1..5 + 2")
        }

        // 연속된 피연산자
        assertThrows<IllegalArgumentException> {
            processExpression("1 1 + 2")
        }
    }
}