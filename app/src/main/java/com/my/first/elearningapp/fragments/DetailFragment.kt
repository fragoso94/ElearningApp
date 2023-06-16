package com.my.first.elearningapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.lifecycle.coroutineScope
import androidx.room.Room
import com.my.first.elearningapp.R
import com.my.first.elearningapp.database.ElearningDatabase
import com.my.first.elearningapp.database.entities.ShoppingEntity
import com.my.first.elearningapp.database.entities.UserEntity
import com.my.first.elearningapp.database.utilities.Helpers
import com.my.first.elearningapp.databinding.FragmentDetailBinding
//import com.my.first.elearningapp.model.COURSE_ID
import com.my.first.elearningapp.model.Course
import com.my.first.elearningapp.model.CourseClickListener
//import com.my.first.elearningapp.model.listCourses
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailFragment : Fragment(R.layout.fragment_detail), CourseClickListener {

    private lateinit var binding: FragmentDetailBinding
    //private var myIdCourses = mutableListOf<Int>()

    private lateinit var database: ElearningDatabase

    override fun onClick(course: Course)
    {
        //Lógica al hacer click sobre el detalle del curso
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentDetailBinding.bind(view)

        initDatabase(view)
        //val courseID = arguments?.getInt(Helpers.COURSE_ID, -1)!!
        //val course = courseFromID(courseID)
        val course: Course? = arguments?.getParcelable(Helpers.COURSE_ITEM)
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
                            val bought = courseIds.contains(course.id) //Verificamos si el curso se encuentra en nuestra lista de cursos comprados.
                            if (bought){
                                buyCourse(course.id, user.id)
                                Toast.makeText(context,"Haz adquirido el curso de ${course.name}", Toast.LENGTH_SHORT).show()
                            }
                            else{
                                Toast.makeText(context,"Ya has adquirido este curso", Toast.LENGTH_SHORT).show()
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

    /*private fun courseFromID(courseID: Int): Course? {

        for(course in listCourses) {
            if(course.id == courseID)
                return course
        }
        return null
    }*/

}