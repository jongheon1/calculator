package token

import java.math.BigDecimal

data class Operand(val value: BigDecimal) : Token {
    constructor(value: Double) : this(BigDecimal(value.toString()))
    constructor(value: Int) : this(BigDecimal(value))
}