package com.my.first.elearningapp.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.my.first.elearningapp.database.dao.UserDao
import com.my.first.elearningapp.database.entities.ShoppingEntity
import com.my.first.elearningapp.database.entities.UserEntity
import com.my.first.elearningapp.database.utilities.Convertes

@Database(entities = [UserEntity::class, ShoppingEntity::class], version = 1, exportSchema = false)
@TypeConverters(Convertes::class)
abstract class ElearningDatabase: RoomDatabase() {
    abstract fun getUserDao(): UserDao

    companion object{
        const val DATABASE_NAME = "db_elearning"
    }
}