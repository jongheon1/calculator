package token

import java.math.BigDecimal
import java.math.MathContext
import java.math.RoundingMode

data class Operand(val value: BigDecimal) : Token {

    companion object {
        private val CALCULATION_CONTEXT = MathContext(32, RoundingMode.HALF_EVEN)
        private val DISPLAY_CONTEXT = MathContext(4, RoundingMode.HALF_EVEN)
    }

    constructor(value: Double) : this(BigDecimal(value.toString()))
    constructor(value: Int) : this(BigDecimal(value))
    constructor(value: Long) : this(BigDecimal(value))

    fun add(rhs: Operand): Operand =
        Operand(this.value.add(rhs.value, CALCULATION_CONTEXT))

    fun subtract(rhs: Operand): Operand =
        Operand(this.value.subtract(rhs.value, CALCULATION_CONTEXT))

    fun multiply(rhs: Operand): Operand =
        Operand(this.value.multiply(rhs.value, CALCULATION_CONTEXT))

    fun divide(rhs: Operand): Operand =
        Operand(this.value.divide(rhs.value, CALCULATION_CONTEXT))

    fun mod(rhs: Operand): Operand =
        Operand(this.value.remainder(rhs.value, CALCULATION_CONTEXT))

    fun pow(rhs: Operand): Operand =
        Operand(this.value.pow(rhs.value.toInt(), CALCULATION_CONTEXT)) // int 가 아닌 값으로 나누면 에러 나오도록 수정 해야 함

    override fun toString(): String =
        value.round(DISPLAY_CONTEXT)
            .stripTrailingZeros()
            .toPlainString()
}