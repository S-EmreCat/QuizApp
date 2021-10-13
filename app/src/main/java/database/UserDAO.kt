package database

import androidx.room.*
import database.UserEntity
import androidx.sqlite.db.SimpleSQLiteQuery

import androidx.sqlite.db.SupportSQLiteQuery




@Dao
interface UserDAO {
    // data access object
    // coroutine scope içerisinde çalıştığımız için suspend ile fun oluşturuyoruz.

    @Insert
    suspend fun insert(user: UserEntity)

    @Query("SELECT * FROM users")
    fun getAllUser():List<UserEntity>

    @Query("SELECT * FROM users WHERE userName=:getUsername AND userPassword=:getPassword")
    suspend fun getUser(getUsername:String,getPassword:String): UserEntity?

    @Query("SELECT * FROM users WHERE userId=:getUserid")
    fun getQuizUser(getUserid:Int): UserEntity?

    @Query("SELECT * FROM users WHERE userName=:getUsername")
    suspend fun getUserName(getUsername: String): UserEntity?

    @Query("SELECT * FROM users where userScore>0 ORDER BY userScore DESC  ")
    suspend fun getScoreTable():List<UserEntity>
    @Query("SELECT * FROM users  where userScore>0 ORDER BY userScore DESC LIMIT:limit")
    suspend fun getScoreTable3(limit: Int=3):List<UserEntity>
    @Update
    suspend fun update(user:UserEntity)

    @Query("DELETE FROM users")
    suspend fun deleteAll()

}
