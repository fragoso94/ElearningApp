package com.my.first.elearningapp.database.utilities

import android.content.Context
import android.content.res.Resources
import android.util.Log
import com.my.first.elearningapp.R
import com.my.first.elearningapp.model.Course
import com.my.first.elearningapp.model.CourseResponse

class Helpers {
    companion object{
        const val COURSE_ID = "courseID"
        const val URL_BASE_API = "http://192.168.1.164:9095/"

        fun convertDataClass(context: Context, dataClassA: CourseResponse): Course {
            val resources: Resources = context.resources
            val packageName: String = context.packageName
            val image = resources.getIdentifier(dataClassA.image, "drawable", packageName)
            //Course(++cont, R.drawable.photoshop, "Diseño","Photoshop", 40F, 269.90F, 3.5F),
            with(dataClassA){
                return Course(id, image, category, name, duration, price, rating)
            }
        }

        fun convertListDataClass(context: Context, dataClassA: List<CourseResponse>): List<Course> {
            val resources: Resources = context.resources
            val packageName: String = context.packageName

            val courses: MutableList<Course> = mutableListOf()
            for (item in dataClassA){
                val image = resources.getIdentifier(item.image, "drawable", packageName)
                with(item){
                    val course = Course(id, image, category, name, duration, price, rating)
                    courses.add(course)
                    //Log.d("dfragoso94", course.toString())
                }
            }

            return courses
        }

    }
}