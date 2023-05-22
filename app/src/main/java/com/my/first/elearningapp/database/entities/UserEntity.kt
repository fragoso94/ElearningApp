package com.my.first.elearningapp.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull

@Entity(tableName = "user_table")
data class UserEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Int = 0,

    @ColumnInfo(name = "name")
    var name: String,

    @ColumnInfo(name = "email")
    var email: String,

    @ColumnInfo(name = "mobile")
    var mobile: String,

    @ColumnInfo(name = "password")
<<<<<<< HEAD
    var password: String
=======
    val password: String,

    @ColumnInfo(name = "status")
    val status: Boolean
>>>>>>> fd26fee216d5170650a7746427fde69f33aaaa2f
)