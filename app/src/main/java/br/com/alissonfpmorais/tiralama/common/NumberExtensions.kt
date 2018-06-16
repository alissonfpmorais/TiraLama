package br.com.alissonfpmorais.tiralama.common

import java.util.*

fun ClosedRange<Int>.random() = Random().nextInt(endInclusive - start) +  start