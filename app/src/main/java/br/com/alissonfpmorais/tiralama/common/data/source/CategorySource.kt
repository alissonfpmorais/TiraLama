package br.com.alissonfpmorais.tiralama.common.data.source

import br.com.alissonfpmorais.tiralama.common.data.local.entity.Category

interface CategorySource {
    fun insertCategory(category: Category): Long
    fun listCategories(): List<Category>
    fun updateCategory(category: Category): Int
    fun truncate()
}