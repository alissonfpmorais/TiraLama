package br.com.alissonfpmorais.tiralama.common.data.local.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import android.arch.persistence.room.Update
import br.com.alissonfpmorais.tiralama.common.data.local.entity.Category
import br.com.alissonfpmorais.tiralama.common.data.source.CategorySource

@Dao
interface CategoryDao : CategorySource {
    @Insert
    override fun insertCategory(category: Category): Long

    @Query("select * from category")
    override fun listCategories(): List<Category>

    @Update
    override fun updateCategory(category: Category): Int

    @Query("delete from category")
    override fun truncate()
}