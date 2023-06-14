package com.my.first.elearningapp.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.my.first.elearningapp.R
import com.my.first.elearningapp.adapter.RecyclerAdapter
import com.my.first.elearningapp.api.Api
import com.my.first.elearningapp.database.utilities.Helpers
import com.my.first.elearningapp.databinding.FragmentHomeBinding
import com.my.first.elearningapp.home.DetailActivity
//import com.my.first.elearningapp.model.COURSE_ID
import com.my.first.elearningapp.model.Course
import com.my.first.elearningapp.model.CourseClickListener
import com.my.first.elearningapp.model.CourseResponse
import kotlinx.coroutines.*
import retrofit2.HttpException

//import com.my.first.elearningapp.model.listCourses

class HomeFragment : Fragment(R.layout.fragment_home ) {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var contexto: Context
    private var listCourses: List<Course> = listOf()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            contexto = context
        } catch (e: ClassCastException) {
            throw ClassCastException("$context debe implementar ParametrosListener")
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view)

        //Obtenemos la lista de cursos de la Api
        CoroutineScope(Dispatchers.IO).launch {
            val deferred = async { getCourses() }
            val response = deferred.await()
            //Log.d("dfragoso94", response.toString())
            if(response != null)
            {
                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        contexto,
                        "Los cursos se obtuvieron correctamente..",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                listCourses = Helpers.convertListDataClass(contexto, response)
                //Log.d("dfragoso94", listCourses.toString())

                CoroutineScope(Dispatchers.Main).launch {
                    //val courseFragment = this
                    var coursesFiltered: List<Course>
                    Log.d("dfragoso","Comenzando el pintado de datos...")
                    binding.recycler.apply {
                        layoutManager = LinearLayoutManager(view.context)
                        adapter = RecyclerAdapter(listCourses, { course -> onItemSelected(course) }) //courseFragment
                    }

                    binding.buttonAll.setOnClickListener{
                        binding.recycler.adapter = RecyclerAdapter(listCourses, { course -> onItemSelected(course) }) //this
                    }

                    binding.buttonDesign.setOnClickListener{
                        coursesFiltered = listCourses.filter { c -> c.category.contains("Diseño") }
                        binding.recycler.adapter = RecyclerAdapter(coursesFiltered, { course -> onItemSelected(course) }) //this
                    }

                    binding.buttonProgrammation.setOnClickListener {
                        coursesFiltered = listCourses.filter { c -> c.category.contains("Programación") }
                        binding.recycler.adapter = RecyclerAdapter(coursesFiltered, { course -> onItemSelected(course) }) //this
                    }

                    binding.buttonWeb.setOnClickListener {
                        coursesFiltered = listCourses.filter { c -> c.category.contains("Desarrollo Web") }
                        binding.recycler.adapter = RecyclerAdapter(coursesFiltered, { course -> onItemSelected(course) }) //this
                    }
                }

            }
            else
            {
                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        contexto,
                        "Algo falló en la API.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

    }

    private fun onItemSelected(course: Course){
        try{
            Log.d("dfragoso94",course.toString())
            val activity = contexto //requireActivity()
            val intent = Intent(activity, DetailActivity::class.java)
            intent.putExtra(Helpers.COURSE_ID, course.id.toString())
            activity.startActivity(intent)
        }
        catch (e: Exception){
            Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
        }
    }

    private suspend fun getCourses(): List<CourseResponse>?{
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
                    contexto,
                    e.message.toString(), //"Error de la API",
                    Toast.LENGTH_LONG
                ).show()
            }
            Log.d("dfragoso94", e.message.toString())
        }
        return response
    }

}