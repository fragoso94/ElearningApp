package com.my.first.elearningapp.database

import android.content.Context
import androidx.room.*
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
    /*companion object{
        private var elearningInstance: ElearningDatabase? = null
        const val DATABASE_NAME = "db_elearning"

        fun getInstance(context: Context) : ElearningDatabase {
            return elearningInstance?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ElearningDatabase::class.java,
                    DATABASE_NAME
                ).build()
                elearningInstance = instance
                // return instance
                instance
            }
        }
    }*/
}