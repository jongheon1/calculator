import calculator.Calculator
import org.junit.jupiter.api.assertThrows
import token.*
import java.math.BigDecimal
import kotlin.test.Test
import kotlin.test.assertEquals

class CalculatorTest {
    private val calculator = Calculator()

    @Test
    fun `단순 덧셈 계산`() {
        val tokens = listOf(
            Operand(1),
            Operand(2),
            PlusOperator
        )

        val result = calculator.calculate(tokens)

        assertEquals(
            expected = BigDecimal(3),
            actual = result
        )
    }

    @Test
    fun `단순 곱셈 계산`() {
        val tokens = listOf(
            Operand(4),
            Operand(5),
            MultiplyOperator
        )

        val result = calculator.calculate(tokens)

        assertEquals(
            expected = BigDecimal(20),
            actual = result
        )
    }

    @Test
    fun `복합 연산 계산`() {
        // 1 2 3 * + (즉, 1 + (2 * 3))
        val tokens = listOf(
            Operand(1),
            Operand(2),
            Operand(3),
            MultiplyOperator,
            PlusOperator
        )

        val result = calculator.calculate(tokens)

        assertEquals(
            expected = BigDecimal(7),
            actual = result
        )
    }

    @Test
    fun `여러 연산자가 섞인 복잡한 계산`() {
        // 1 2 3 * 4 5 / + + (즉, 1 + (2 * 3) + (4 / 5))
        val tokens = listOf(
            Operand(1),
            Operand(2),
            Operand(3),
            MultiplyOperator,
            Operand(4),
            Operand(5),
            DivideOperator,
            PlusOperator,
            PlusOperator
        )

        val result = calculator.calculate(tokens)

        assertEquals(
            expected = BigDecimal("7.8"),
            actual = result
        )
    }

    @Test
    fun `음수를 포함한 계산`() {
        // -1 -2 + (즉, (-1) + (-2))
        val tokens = listOf(
            Operand(-1),
            Operand(-2),
            PlusOperator
        )

        val result = calculator.calculate(tokens)

        assertEquals(
            expected = BigDecimal(-3),
            actual = result
        )
    }

    @Test
    fun `0으로 나누기 시도`() {
        val tokens = listOf(
            Operand(1),
            Operand(0),
            DivideOperator
        )

        assertThrows<IllegalArgumentException> {
            calculator.calculate(tokens)
        }
    }

    @Test
    fun `단일 피연산자`() {
        val tokens = listOf(Operand(42))

        val result = calculator.calculate(tokens)

        assertEquals(
            expected = BigDecimal(42),
            actual = result
        )
    }

    @Test
    fun `큰 숫자의 연산`() {
        val tokens = listOf(
            Operand(9999999),
            Operand(9999999),
            MultiplyOperator
        )

        val result = calculator.calculate(tokens)

        assertEquals(
            expected = BigDecimal("99999980000001"),
            actual = result
        )
    }

    @Test
    fun `소수점이 있는 연산`() {
        val tokens = listOf(
            Operand(BigDecimal("1.5")),
            Operand(BigDecimal("2.3")),
            PlusOperator
        )

        val result = calculator.calculate(tokens)

        assertEquals(
            expected = BigDecimal("3.8"),
            actual = result
        )
    }
}