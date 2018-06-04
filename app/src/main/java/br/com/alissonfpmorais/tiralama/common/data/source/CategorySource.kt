package br.com.alissonfpmorais.tiralama.common.data.source

import br.com.alissonfpmorais.tiralama.common.data.local.entity.Category

interface CategorySource {
    fun insertCategory(category: Category)
    fun listCategories(): List<Category>
    fun updateCategory(category: Category)
    fun truncate()
}