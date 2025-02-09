import calculator.PostfixConverter
import org.junit.jupiter.api.assertThrows
import token.*
import kotlin.test.Test
import kotlin.test.assertContentEquals

class PostfixConverterTest {
    private val converter = PostfixConverter()

    @Test
    fun `단순 덧셈 변환`() {
        val tokens = listOf(
            Operand(1),
            PlusOperator,
            Operand(2)
        )

        val result = converter.convertToPostfix(tokens)

        assertContentEquals(
            expected = listOf(
                Operand(1),
                Operand(2),
                PlusOperator
            ),
            actual = result
        )
    }

    @Test
    fun `연산자 우선순위 테스트`() {
        // 1 + 2 * 3
        val tokens = listOf(
            Operand(1),
            PlusOperator,
            Operand(2),
            MultiplyOperator,
            Operand(3)
        )

        val result = converter.convertToPostfix(tokens)

        assertContentEquals(
            expected = listOf(
                Operand(1),
                Operand(2),
                Operand(3),
                MultiplyOperator,
                PlusOperator
            ),
            actual = result
        )
    }

    @Test
    fun `괄호가 있는 수식 변환`() {
        // (1 + 2) * 3
        val tokens = listOf(
            LeftParenthesis,
            Operand(1),
            PlusOperator,
            Operand(2),
            RightParenthesis,
            MultiplyOperator,
            Operand(3)
        )

        val result = converter.convertToPostfix(tokens)

        assertContentEquals(
            expected = listOf(
                Operand(1),
                Operand(2),
                PlusOperator,
                Operand(3),
                MultiplyOperator
            ),
            actual = result
        )
    }

    @Test
    fun `복잡한 괄호 수식 변환`() {
        // ((1 + 2) * (3 + 4))
        val tokens = listOf(
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
        )

        val result = converter.convertToPostfix(tokens)

        assertContentEquals(
            expected = listOf(
                Operand(1),
                Operand(2),
                PlusOperator,
                Operand(3),
                Operand(4),
                PlusOperator,
                MultiplyOperator
            ),
            actual = result
        )
    }

    @Test
    fun `연속된 연산자 변환`() {
        // 1 + 2 + 3 + 4
        val tokens = listOf(
            Operand(1),
            PlusOperator,
            Operand(2),
            PlusOperator,
            Operand(3),
            PlusOperator,
            Operand(4)
        )

        val result = converter.convertToPostfix(tokens)

        assertContentEquals(
            expected = listOf(
                Operand(1),
                Operand(2),
                PlusOperator,
                Operand(3),
                PlusOperator,
                Operand(4),
                PlusOperator
            ),
            actual = result
        )
    }

    @Test
    fun `우선순위가 다른 연산자들 혼합`() {
        // 1 + 2 * 3 - 4 / 5
        val tokens = listOf(
            Operand(1),
            PlusOperator,
            Operand(2),
            MultiplyOperator,
            Operand(3),
            MinusOperator,
            Operand(4),
            DivideOperator,
            Operand(5)
        )

        val result = converter.convertToPostfix(tokens)

        assertContentEquals(
            expected = listOf(
                Operand(1),
                Operand(2),
                Operand(3),
                MultiplyOperator,
                PlusOperator,
                Operand(4),
                Operand(5),
                DivideOperator,
                MinusOperator
            ),
            actual = result
        )
    }

    @Test
    fun `중첩된 복잡한 괄호 수식`() {
        // (1 + (2 + 3) * (4 + 5))
        val tokens = listOf(
            LeftParenthesis,
            Operand(1),
            PlusOperator,
            LeftParenthesis,
            Operand(2),
            PlusOperator,
            Operand(3),
            RightParenthesis,
            MultiplyOperator,
            LeftParenthesis,
            Operand(4),
            PlusOperator,
            Operand(5),
            RightParenthesis,
            RightParenthesis
        )

        val result = converter.convertToPostfix(tokens)

        assertContentEquals(
            expected = listOf(
                Operand(1),
                Operand(2),
                Operand(3),
                PlusOperator,
                Operand(4),
                Operand(5),
                PlusOperator,
                MultiplyOperator,
                PlusOperator
            ),
            actual = result
        )
    }

    @Test
    fun `단일 피연산자 변환`() {
        val tokens = listOf(Operand(1))

        val result = converter.convertToPostfix(tokens)

        assertContentEquals(
            expected = listOf(Operand(1)),
            actual = result
        )
    }

    @Test
    fun `음수를 포함한 수식 변환`() {
        // -1 + 2 * -3
        val tokens = listOf(
            Operand(-1),
            PlusOperator,
            Operand(2),
            MultiplyOperator,
            Operand(-3)
        )

        val result = converter.convertToPostfix(tokens)

        assertContentEquals(
            expected = listOf(
                Operand(-1),
                Operand(2),
                Operand(-3),
                MultiplyOperator,
                PlusOperator
            ),
            actual = result
        )
    }

    @Test
    fun `빈 표현식 검증`() {
        assertThrows<IllegalArgumentException> {
            converter.convertToPostfix(emptyList())
        }
    }

    @Test
    fun `연산자로 시작하는 경우`() {
        // + 1 2
        val tokens = listOf(
            PlusOperator,
            Operand(1),
            Operand(2)
        )

        assertThrows<IllegalArgumentException> {
            converter.convertToPostfix(tokens)
        }
    }

    @Test
    fun `연산자로 끝나는 경우`() {
        // 1 2 +
        val tokens = listOf(
            Operand(1),
            Operand(2),
            PlusOperator
        )

        assertThrows<IllegalArgumentException> {
            converter.convertToPostfix(tokens)
        }
    }

    @Test
    fun `연속된 연산자`() {
        // 1 + * 2
        val tokens = listOf(
            Operand(1),
            PlusOperator,
            MultiplyOperator,
            Operand(2)
        )

        assertThrows<IllegalArgumentException> {
            converter.convertToPostfix(tokens)
        }
    }

    @Test
    fun `연속된 피연산자`() {
        // 1 2 +
        val tokens = listOf(
            Operand(1),
            Operand(2),
            PlusOperator
        )

        assertThrows<IllegalArgumentException> {
            converter.convertToPostfix(tokens)
        }
    }

    @Test
    fun `닫는 괄호가 먼저 나오는 경우`() {
        // ) 1 + 2
        val tokens = listOf(
            RightParenthesis,
            Operand(1),
            PlusOperator,
            Operand(2)
        )

        assertThrows<IllegalArgumentException> {
            converter.convertToPostfix(tokens)
        }
    }

    @Test
    fun `여는 괄호만 있는 경우`() {
        // 1 + ( 2
        val tokens = listOf(
            Operand(1),
            PlusOperator,
            LeftParenthesis,
            Operand(2)
        )

        assertThrows<IllegalArgumentException> {
            converter.convertToPostfix(tokens)
        }
    }

    @Test
    fun `빈 괄호`() {
        // 1 + ( )
        val tokens = listOf(
            Operand(1),
            PlusOperator,
            LeftParenthesis,
            RightParenthesis
        )

        assertThrows<IllegalArgumentException> {
            converter.convertToPostfix(tokens)
        }
    }

    @Test
    fun `괄호 바로 뒤에 연산자`() {
        // ( + 1 )
        val tokens = listOf(
            LeftParenthesis,
            PlusOperator,
            Operand(1),
            RightParenthesis
        )

        assertThrows<IllegalArgumentException> {
            converter.convertToPostfix(tokens)
        }
    }

    @Test
    fun `괄호 앞에 피연산자`() {
        // 1 ( 2 )
        val tokens = listOf(
            Operand(1),
            LeftParenthesis,
            Operand(2),
            RightParenthesis
        )

        assertThrows<IllegalArgumentException> {
            converter.convertToPostfix(tokens)
        }
    }

    @Test
    fun `연산자 다음에 닫는 괄호`() {
        // ( 1 + )
        val tokens = listOf(
            LeftParenthesis,
            Operand(1),
            PlusOperator,
            RightParenthesis
        )

        assertThrows<IllegalArgumentException> {
            converter.convertToPostfix(tokens)
        }
    }

}