package br.com.alissonfpmorais.tiralama.common.data.local.dao

import android.arch.persistence.room.*
import br.com.alissonfpmorais.tiralama.common.data.local.entity.User
import br.com.alissonfpmorais.tiralama.common.data.source.UserSource

@Dao
interface UserDao : UserSource {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    override fun insertUser(user: User)

    @Query("select * from user where username = :username and password = :password")
    override fun listByUsernameAndPassword(username: String, password: String): List<User>

    @Query("select * from user where isLogged = :isLogged")
    override fun listByLogged(isLogged: Boolean): List<User>

    @Update(onConflict = OnConflictStrategy.REPLACE)
    override fun updatePassword(user: User)

    @Query("delete from user")
    override fun truncate()
}