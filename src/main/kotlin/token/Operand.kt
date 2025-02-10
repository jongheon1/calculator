package token

import java.math.BigDecimal

data class Operand(val value: BigDecimal) : Token {
    constructor(value: Double) : this(BigDecimal(value.toString()))
    constructor(value: Int) : this(BigDecimal(value))

    fun add(rhs: Operand): Operand =
        Operand(this.value.add(rhs.value))

    fun subtract(rhs: Operand): Operand =
        Operand(this.value.subtract(rhs.value))

    fun multiply(rhs: Operand): Operand =
        Operand(this.value.multiply(rhs.value))

    fun divide(rhs: Operand): Operand =
        Operand(this.value.divide(rhs.value))
}