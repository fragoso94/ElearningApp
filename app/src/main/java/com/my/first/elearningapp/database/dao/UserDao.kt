package com.my.first.elearningapp.database.dao

import androidx.room.*
import com.my.first.elearningapp.database.entities.ShoppingEntity
import com.my.first.elearningapp.database.entities.UserEntity

@Dao
interface UserDao {
    @Query("select * from user_table order by name")
    suspend fun getAllUser(): List<UserEntity>

    @Query("select * from user_table where name = :userName")
    suspend fun getUserId(userName: String): UserEntity

    @Insert //(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(user:UserEntity)

    @Update
    suspend fun update(user: UserEntity)

    @Delete
    suspend fun delete(user: UserEntity)

    @Query("select idCourse from shopping_table where idUser = :userId")
    suspend fun getAllShopping(userId: Int): List<Int>

    @Insert
    suspend fun insertShopping(shopping: ShoppingEntity)
}