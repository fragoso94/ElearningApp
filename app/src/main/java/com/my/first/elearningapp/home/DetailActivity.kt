package com.my.first.elearningapp.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.coroutineScope
import androidx.room.Room
import com.my.first.elearningapp.database.ElearningDatabase
import com.my.first.elearningapp.database.entities.ShoppingEntity
import com.my.first.elearningapp.database.entities.UserEntity
import com.my.first.elearningapp.database.utilities.Helpers
import com.my.first.elearningapp.databinding.ActivityDetailBinding
//import com.my.first.elearningapp.model.COURSE_ID
import com.my.first.elearningapp.model.Course
import com.my.first.elearningapp.model.CourseClickListener
import com.my.first.elearningapp.model.listCourses
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailActivity : AppCompatActivity(), CourseClickListener {

    private lateinit var binding: ActivityDetailBinding

    private lateinit var database: ElearningDatabase

    override fun onClick(course: Course)
    {
        //Lógica al hacer click sobre el detalle del curso
        Log.d("dfragoso94", course.toString())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        initDatabase(view)
        val courseID = intent.getStringExtra(Helpers.COURSE_ID)
        val course = courseID?.let { courseFromID(it.toInt()) }
        //Log.d("dfragoso94","idCurso: $courseID")
        //Log.d("dfragoso94",course.toString())

        if(course != null) {
            binding.courseImage.setImageResource(course.image)
            binding.tvCourse.text = course.name
            binding.tvPrice.text = "$" + String.format("%.2f", course.price)
            binding.tvDuration.text = String.format("%.2f", course.duration) + " horas"
            binding.rbCalification.rating = course.rating

        }

        binding.buttonBuy.setOnClickListener{
            lifecycle.coroutineScope.launch(Dispatchers.IO) {
                val user = getUserLogin()
                if (user != null){
                    val courseIds = getCourseShopping(user.id)
                    lifecycle.coroutineScope.launch(Dispatchers.Main) {
                        if(course != null){
                            if(courseIds.isEmpty()){
                                buyCourse(course.id, user.id)
                                Toast.makeText(applicationContext,"Tienes ${courseIds.size} cursos comprados", Toast.LENGTH_SHORT).show()
                            }
                            else{
                                val bought = courseIds.contains(course.id) //Verificamos si el curso se encuentra en nuestra lista de cursos comprados.
                                if (!bought){
                                    buyCourse(course.id, user.id)
                                    Toast.makeText(applicationContext,"Haz adquirido el curso de ${course.name}", Toast.LENGTH_SHORT).show()
                                }
                                else{
                                    Toast.makeText(applicationContext,"Ya has adquirido este curso", Toast.LENGTH_SHORT).show()
                                }
                            }
                        }
                    }
                }
            }
        }

    }

    private fun initDatabase(view: View){
        database = Room.databaseBuilder(
            view.context, ElearningDatabase::class.java, ElearningDatabase.DATABASE_NAME)
            .allowMainThreadQueries()
            .fallbackToDestructiveMigrationOnDowngrade()
            .build()
    }

    private suspend fun buyCourse(courseID: Int, userID: Int){
        val request = ShoppingEntity(
            idUser = userID,
            idCourse = courseID
        )
        database.getUserDao().insertShopping(request)
    }

    private suspend fun getCourseShopping(idUser: Int): List<Int>{
        var response = database.getUserDao().getAllShopping(idUser)
        return response
    }

    private suspend fun getUserLogin(): UserEntity? {
        var response = database.getUserDao().getUserLogin()
        return response
    }

    private fun courseFromID(courseID: Int): Course? {

        for(course in listCourses) {
            if(course.id == courseID)
                return course
        }
        return null
    }

    override fun onBackPressed() {
        finish()
    }
}