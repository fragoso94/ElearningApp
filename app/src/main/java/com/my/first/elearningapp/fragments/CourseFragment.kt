package com.my.first.elearningapp.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.coroutineScope
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.my.first.elearningapp.R
import com.my.first.elearningapp.adapter.RecyclerAdapter
import com.my.first.elearningapp.api.Api
import com.my.first.elearningapp.database.ElearningDatabase
import com.my.first.elearningapp.database.entities.UserEntity
import com.my.first.elearningapp.database.utilities.Helpers
import com.my.first.elearningapp.database.utilities.SwipeToDeleteCallback
import com.my.first.elearningapp.databinding.FragmentCourseBinding
import com.my.first.elearningapp.home.DetailActivity
import com.my.first.elearningapp.model.Course
import com.my.first.elearningapp.model.CourseClickListener
import com.my.first.elearningapp.model.CourseResponse
import kotlinx.coroutines.*

class CourseFragment : Fragment(), CourseClickListener { //Fragment(R.layout.fragment_course)

    private lateinit var binding: FragmentCourseBinding
    private var myCoursesList = emptyList<Course>() //mutableListOf<Course>()
    private var myIdCourses: List<Int> = emptyList()
    private var listCourses: List<Course> = listOf()

    private lateinit var database: ElearningDatabase

    override fun onClick(course: Course)
    {
        Toast.makeText(context,"El curso dura ${course.duration} horas", Toast.LENGTH_SHORT).show()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_course, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCourseBinding.bind(view)

        //Inicializamos la BD
        initDatabase(view)

        if(Helpers.isInternetAvailable(view.context)){
            //Obtenemos en una corutina los cursos comprados
            lifecycle.coroutineScope.launch(Dispatchers.IO){
                val userResponse = getUserLogin() //deferredUser.await()
                userResponse?.toString()?.let { Log.d("dfragoso94", it) }

                if (userResponse != null){
                    myIdCourses = getCourseShopping(userResponse.id) //deferred.await()

                    //Obtenemos los cursos de la Api
                    val deferred = async { getCourses(view) }
                    val response = deferred.await()
                    if(response != null)
                    {
                        listCourses = Helpers.convertListDataClass(view.context, response)
                        lifecycle.coroutineScope.launch(Dispatchers.Main){
                            if(myIdCourses.isNotEmpty()) {
                                myCoursesList = myCourses(myIdCourses, listCourses)
                                Toast.makeText(context,"Tienes ${myCoursesList.size} cursos comprados", Toast.LENGTH_SHORT).show()
                            }
                            else{
                                Toast.makeText(context,"No tienes cursos comprados", Toast.LENGTH_SHORT).show()
                            }
                            binding.recycler.apply {
                                layoutManager = LinearLayoutManager(view.context)
                                val myAdapter = RecyclerAdapter(myCoursesList, { course -> onItemSelected(course) })
                                adapter = myAdapter//myCourseFragment

//                        val itemTouchHelper = ItemTouchHelper(SwipeToDeleteCallback(myAdapter))
//                        itemTouchHelper.attachToRecyclerView(binding.recycler)
                            }
                        }
                    }
                    else
                    {
                        withContext(Dispatchers.Main) {
                            Toast.makeText(
                                view.context,
                                "Algo falló en la API.",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }

                }
            }
        }
        else
        {
            CoroutineScope(Dispatchers.Main).launch {
                Toast.makeText(
                    view.context,
                    "No hay conexión a internet.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

    }

    private fun onItemSelected(course: Course){
        try{
            val activity = requireActivity()
            val intent = Intent(activity, DetailActivity::class.java)
            //intent.putExtra(Helpers.COURSE_ID, course.id.toString())
            intent.putExtra(Helpers.COURSE_ITEM, course)
            intent.putExtra(Helpers.IS_VIEW_BUY, false)
            activity.startActivity(intent)
        }
        catch (e: Exception){
            Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
        }
    }

    private fun initDatabase(view: View){
        database = Room.databaseBuilder(
            view.context, ElearningDatabase::class.java, ElearningDatabase.DATABASE_NAME)
            .allowMainThreadQueries()
            .fallbackToDestructiveMigrationOnDowngrade()
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

    private suspend fun getCourses(view: View): List<CourseResponse>?{
        var response: List<CourseResponse>? = null

        try
        {
            var result = Api.courseService.getCourses()
            response = result.body()
            //Log.d("dfragoso94", response.toString())
        }
        catch (e: Exception)
        {
            withContext(Dispatchers.Main) {
                Toast.makeText(
                    view.context,
                    e.message.toString(), //"Error de la API",
                    Toast.LENGTH_LONG
                ).show()
            }
            Log.d("dfragoso94", e.message.toString())
        }
        return response
    }
}



private fun myCourses(myIds: List<Int>, courses: List<Course>): List<Course> {

    val myCourses = courses.filter { course ->
        myIds.contains(course.id)
    }
    return myCourses
}