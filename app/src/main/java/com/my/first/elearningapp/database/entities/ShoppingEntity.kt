package com.my.first.elearningapp.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.my.first.elearningapp.database.utilities.Convertes
import java.time.LocalDateTime
import java.util.Date

@Entity(tableName = "shopping_table")
data class ShoppingEntity (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Int = 0,

    @ColumnInfo(name = "idUser")
    val idUser: Int,

    @ColumnInfo(name = "idCourse")
    val idCourse: Int,

    @ColumnInfo(name = "date")
    @TypeConverters(Convertes::class)
    val date: Date = Date()
)