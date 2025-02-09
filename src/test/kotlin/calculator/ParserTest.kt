package calculator

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.assertThrows
import token.*
import kotlin.test.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertFails

class ParserTest {

    private val parser = Parser()

    @Test
    fun `단일 숫자 파싱`() {
        val input = "123"
        val tokens = parser.parse(input)

        assertContentEquals(
            expected = listOf(Operand(123)),
            actual = tokens
        )
    }

    @Test
    fun `공백이 포함된 수식 파싱`() {
        val input = "1 + 2   * 3"
        val tokens = parser.parse(input)

        assertContentEquals(
            expected = listOf(
                Operand(1),
                PlusOperator,
                Operand(2),
                MultiplyOperator,
                Operand(3)
            ),
            actual = tokens
        )
    }

    @Test
    fun `음수 파싱`() {
        val input = "-1+2"
        val tokens = parser.parse(input)

        assertContentEquals(
            expected = listOf(
                Operand(-1),
                PlusOperator,
                Operand(2)
            ),
            actual = tokens
        )
    }

    @Test
    fun `연산자 다음의 음수 파싱`() {
        val input = "1+-2"
        val tokens = parser.parse(input)

        assertContentEquals(
            expected = listOf(
                Operand(1),
                PlusOperator,
                Operand(-2)
            ),
            actual = tokens
        )
    }

    @Test
    fun `괄호와 음수를 포함한 수식 파싱`() {
        val input = "(-1+2)*3"
        val tokens = parser.parse(input)

        assertContentEquals(
            expected = listOf(
                LeftParenthesis,
                Operand(-1),
                PlusOperator,
                Operand(2),
                RightParenthesis,
                MultiplyOperator,
                Operand(3)
            ),
            actual = tokens
        )
    }

    @Test
    fun `소수점이 포함된 수식 파싱`() {
        val input = "1.5+2.7"
        val tokens = parser.parse(input)

        assertContentEquals(
            expected = listOf(
                Operand(1.5),
                PlusOperator,
                Operand(2.7)
            ),
            actual = tokens
        )
    }

    @Test
    fun `여러 자리 숫자 파싱`() {
        val input = "123+456"
        val tokens = parser.parse(input)

        assertContentEquals(
            expected = listOf(
                Operand(123),
                PlusOperator,
                Operand(456)
            ),
            actual = tokens
        )
    }

    @Test
    fun `복잡한 괄호 수식 파싱`() {
        val input = "((1+2)*(3+4))"
        val tokens = parser.parse(input)

        assertContentEquals(
            expected = listOf(
                LeftParenthesis,
                LeftParenthesis,
                Operand(1),
                PlusOperator,
                Operand(2),
                RightParenthesis,
                MultiplyOperator,
                LeftParenthesis,
                Operand(3),
                PlusOperator,
                Operand(4),
                RightParenthesis,
                RightParenthesis
            ),
            actual = tokens
        )
    }

    @Test
    fun `곱셈과 나눗셈만 있는 수식 파싱`() {
        val input = "2*3/4*5"
        val tokens = parser.parse(input)

        assertContentEquals(
            expected = listOf(
                Operand(2),
                MultiplyOperator,
                Operand(3),
                DivideOperator,
                Operand(4),
                MultiplyOperator,
                Operand(5)
            ),
            actual = tokens
        )
    }

    @Test
    fun `연속된 음수 기호 파싱`() {
        val input = "1--2"  // 1 - (-2)와 동일
        val tokens = parser.parse(input)

        assertContentEquals(
            expected = listOf(
                Operand(1),
                MinusOperator,
                Operand(-2)
            ),
            actual = tokens
        )
    }

    @Test
    fun `잘못된 문자가 포함된 경우 예외 발생`() {
        assertThrows<IllegalArgumentException> {
            parser.parse("1+a")
        }
        assertThrows<IllegalArgumentException> {
            parser.parse("1_2")
        }
    }

    @Test
    fun `소수점이 연속으로 있는 경우 예외 발생`() {
        assertThrows<IllegalArgumentException> {
            parser.parse("1..5")
        }
    }
}