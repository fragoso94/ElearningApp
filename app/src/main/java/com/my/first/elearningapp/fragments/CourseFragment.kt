package com.my.first.elearningapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.my.first.elearningapp.R
import com.my.first.elearningapp.adapter.RecyclerAdapter
import com.my.first.elearningapp.databinding.FragmentCourseBinding
import com.my.first.elearningapp.model.Course
import com.my.first.elearningapp.model.CourseClickListener
import com.my.first.elearningapp.model.listCourses

class CourseFragment : Fragment(R.layout.fragment_course), CourseClickListener {

    private lateinit var binding: FragmentCourseBinding
    private var myCoursesList = mutableListOf<Course>()

    override fun onClick(course: Course)
    {
        Toast.makeText(context,"El curso dura ${course.duration} horas", Toast.LENGTH_SHORT).show()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentCourseBinding.bind(view)

        if(myIdCourses.size > 0) {
            myCoursesList = myCourses(myIdCourses, listCourses)
            Toast.makeText(context,"Tienes ${myCoursesList.size} cursos comprados", Toast.LENGTH_SHORT).show()
        }
        else{
            Toast.makeText(context,"No tienes cursos comprados", Toast.LENGTH_SHORT).show()
        }

        val myCourseFragment = this

        binding.recycler.apply {
            layoutManager = LinearLayoutManager(view.context)
            adapter = RecyclerAdapter(myCoursesList, myCourseFragment)
        }
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