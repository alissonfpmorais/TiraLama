package br.com.alissonfpmorais.tiralama.common

import java.math.BigDecimal
import java.text.NumberFormat
import java.util.*

fun ClosedRange<Int>.random() = Random().nextInt(endInclusive - start) +  start

fun Float.toCurrency(): String =
        NumberFormat.getCurrencyInstance(Locale("pt", "BR")).format(BigDecimal(this.toDouble()))