package com.martafode.lib.ui.utils

import java.math.BigDecimal
import java.math.RoundingMode

fun BigDecimal.toPrice() = setScale((if (hasDecimals()) 2 else 0), RoundingMode.UP)

private fun BigDecimal.hasDecimals(): Boolean = rem(BigDecimal.ONE) != BigDecimal.ZERO.setScale(this.scale())
