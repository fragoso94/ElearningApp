package com.my.first.elearningapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.my.first.elearningapp.R
import com.my.first.elearningapp.adapter.RecyclerAdapter
import com.my.first.elearningapp.databinding.FragmentHomeBinding
import com.my.first.elearningapp.model.COURSE_ID
import com.my.first.elearningapp.model.Course
import com.my.first.elearningapp.model.CourseClickListener
import com.my.first.elearningapp.model.listCourses

class HomeFragment : Fragment(R.layout.fragment_home ), CourseClickListener {

    private lateinit var binding:  FragmentHomeBinding

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
}