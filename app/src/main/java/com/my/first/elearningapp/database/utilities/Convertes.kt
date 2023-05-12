package com.my.first.elearningapp.database.utilities

import androidx.room.TypeConverter
import java.util.*

class Convertes {
    @TypeConverter
    fun fromDate(date: Date): Long {
        return date.time
    }

    @TypeConverter
    fun toDate(timestamp: Long): Date {
        return Date(timestamp)
    }
}