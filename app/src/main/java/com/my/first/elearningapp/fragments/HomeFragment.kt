package com.my.first.elearningapp.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import androidx.lifecycle.coroutineScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.my.first.elearningapp.R
import com.my.first.elearningapp.adapter.RecyclerAdapter
import com.my.first.elearningapp.database.ElearningDatabase
import com.my.first.elearningapp.database.entities.UserEntity
import com.my.first.elearningapp.databinding.FragmentHomeBinding
import com.my.first.elearningapp.model.COURSE_ID
import com.my.first.elearningapp.model.Course
import com.my.first.elearningapp.model.CourseClickListener
import com.my.first.elearningapp.model.listCourses
import kotlinx.coroutines.launch

class HomeFragment : Fragment(R.layout.fragment_home ), CourseClickListener {

    private lateinit var binding: FragmentHomeBinding

    override fun onClick(course: Course)
    {
        val bundle = Bundle()
        bundle.putInt(COURSE_ID, course.id)
        //findNavController().navigate(R.id.action_coursesFragment_to_detailFragment, bundle)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentHomeBinding.bind(view)
        val courseFragment = this
        var coursesFiltered: List<Course>

        binding.recycler.apply {
            layoutManager = LinearLayoutManager(view.context)
            adapter = RecyclerAdapter(listCourses, courseFragment)
        }

        binding.buttonAll.setOnClickListener{
            binding.recycler.adapter = RecyclerAdapter(listCourses, this)
        }

        binding.buttonDesign.setOnClickListener{
            coursesFiltered = listCourses.filter { c -> c.category.contains("Diseño") }
            binding.recycler.adapter = RecyclerAdapter(coursesFiltered, this)
        }

        binding.buttonProgrammation.setOnClickListener {
            coursesFiltered = listCourses.filter { c -> c.category.contains("Programación") }
            binding.recycler.adapter = RecyclerAdapter(coursesFiltered, this)
        }

        binding.buttonWeb.setOnClickListener {
            coursesFiltered = listCourses.filter { c -> c.category.contains("Desarrollo Web") }
            binding.recycler.adapter = RecyclerAdapter(coursesFiltered, this)
        }
    }

    /*private fun navigateFragmentDetail(){
        val fragmentB = DetailFragment() // Instancia del FragmentB que deseas mostrar
        val fragmentManager = requireActivity().supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_container, fragmentB)
        fragmentTransaction.addToBackStack(null) // Agrega la transacción a la pila de retroceso (opcional)
        fragmentTransaction.commit()
    }*/
}