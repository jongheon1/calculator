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

    override fun toString(): String =
        value.round(DISPLAY_CONTEXT)
            .stripTrailingZeros()
            .toPlainString()
}