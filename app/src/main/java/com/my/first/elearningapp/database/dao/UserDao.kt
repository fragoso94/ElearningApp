package com.my.first.elearningapp.database.dao

import androidx.room.*
import com.my.first.elearningapp.database.entities.ShoppingEntity
import com.my.first.elearningapp.database.entities.UserEntity

@Dao
interface UserDao {
    //Métodos del usuario
    @Query("select * from user_table order by name")
    suspend fun getAllUser(): List<UserEntity>

    @Query("select * from user_table where name = :userName")
    suspend fun getUserId(userName: String): UserEntity

    @Query("select * from user_table where email = :userEmail")
    suspend fun getUserEmail(userEmail: String): UserEntity

    @Query("UPDATE user_table SET status = :userStatus WHERE name = :userName")
    suspend fun updateStatusUser(userName: String, userStatus: Boolean)

    @Query("select * from user_table where status = 1 LIMIT 1")
    suspend fun getUserLogin(): UserEntity?

    @Insert //(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(user:UserEntity)

    @Update
    suspend fun update(user: UserEntity)

    @Delete
    suspend fun delete(user: UserEntity)

    // Métodos de compras de curso
    @Query("select idCourse from shopping_table where idUser = :userId")
    suspend fun getAllShopping(userId: Int): List<Int>

    @Insert
    suspend fun insertShopping(shopping: ShoppingEntity)
}