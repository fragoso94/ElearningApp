package com.my.first.elearningapp.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.my.first.elearningapp.database.dao.UserDao
import com.my.first.elearningapp.database.entities.UserEntity

@Database(entities = [UserEntity::class], version = 1, exportSchema = false)
abstract class ElearningDatabase: RoomDatabase() {
    abstract fun getUserDao(): UserDao

    companion object{
        const val DATABASE_NAME = "db_elearning"
    }
}