package com.my.first.elearningapp.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.lifecycle.coroutineScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.my.first.elearningapp.R
import com.my.first.elearningapp.adapter.RecyclerAdapter
import com.my.first.elearningapp.database.ElearningDatabase
import com.my.first.elearningapp.database.entities.UserEntity
import com.my.first.elearningapp.databinding.FragmentCourseBinding
import com.my.first.elearningapp.model.Course
import com.my.first.elearningapp.model.CourseClickListener
import com.my.first.elearningapp.model.listCourses
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class CourseFragment : Fragment(R.layout.fragment_course), CourseClickListener {

    private lateinit var binding: FragmentCourseBinding
    private var myCoursesList = mutableListOf<Course>()
    private var myIdCourses: List<Int> = emptyList()

    private lateinit var database: ElearningDatabase

    override fun onClick(course: Course)
    {
        Toast.makeText(context,"El curso dura ${course.duration} horas", Toast.LENGTH_SHORT).show()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCourseBinding.bind(view)

        //Inicializamos la BD
        initDatabase(view)

        //Obtenemos en una corutina los cursos comprados
        lifecycle.coroutineScope.launch(Dispatchers.IO){
            //val deferredUser = async { getUserLogin() }
            val userResponse = getUserLogin() //deferredUser.await()
            userResponse?.toString()?.let { Log.d("dfragoso94", it) }

            if (userResponse != null){
                //val deferred = async { getCourseShopping(userResponse.id) }
                myIdCourses = getCourseShopping(userResponse.id) //deferred.await()
                Log.d("dfragoso94",myIdCourses.toString())

                lifecycle.coroutineScope.launch(Dispatchers.Main){
                    if(myIdCourses.isNotEmpty()) {
                        myCoursesList = myCourses(myIdCourses, listCourses)
                        //Toast.makeText(context,"Tienes ${myCoursesList.size} cursos comprados", Toast.LENGTH_SHORT).show()
                        Toast.makeText(context,"No Tienes cursos comprados", Toast.LENGTH_SHORT).show()
                    }
                    else{
                        Toast.makeText(context,"No tienes cursos comprados", Toast.LENGTH_SHORT).show()
                    }
                }

            }
        }

        val myCourseFragment = this

        binding.recycler.apply {
            layoutManager = LinearLayoutManager(view.context)
            adapter = RecyclerAdapter(myCoursesList, myCourseFragment)
        }
    }

    private fun initDatabase(view: View){
        database = Room.databaseBuilder(
            view.context, ElearningDatabase::class.java, ElearningDatabase.DATABASE_NAME)
            .allowMainThreadQueries()
            .build()
    }

    private suspend fun getUserLogin(): UserEntity? {
        var response = database.getUserDao().getUserLogin()
        return response
    }

    private suspend fun getCourseShopping(idUser: Int): List<Int>{
        var response = database.getUserDao().getAllShopping(idUser)
        return response
    }

}



private fun myCourses(myIds: List<Int>, courses: List<Course>): MutableList<Course> {

    var myCourses = mutableListOf<Course>()

    for(myCourse in myIds){

        for(course in courses){

            if(myCourse == course.id){
                myCourses.add(course)
                break
            }
        }
    }
    return myCourses
}