package com.my.first.elearningapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import com.my.first.elearningapp.R
import com.my.first.elearningapp.databinding.FragmentDetailBinding
import com.my.first.elearningapp.model.COURSE_ID
import com.my.first.elearningapp.model.Course
import com.my.first.elearningapp.model.CourseClickListener
import com.my.first.elearningapp.model.listCourses

class DetailFragment : Fragment(R.layout.fragment_detail), CourseClickListener {

    private lateinit var binding: FragmentDetailBinding
    private var myIdCourses = mutableListOf<Int>()

    override fun onClick(course: Course)
    {
        //Lógica al hacer click sobre el detalle del curso
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentDetailBinding.bind(view)

        var bought = false

        val courseID = arguments?.getInt(COURSE_ID, -1)!!
        val course = courseFromID(courseID)
        if(course != null) {

            binding.courseImage.setImageResource(course.image)
            binding.tvCourse.text = course.name
            binding.tvPrice.text = "$" + String.format("%.2f", course.price)
            binding.tvDuration.text = String.format("%.2f", course.duration) + " horas"
            binding.rbCalification.rating = course.rating

        }

        binding.buttonBuy.setOnClickListener{

            if(course != null) {
                for(id in myIdCourses) {
                    if(id == course.id)
                        bought = true
                }

                if(!bought) {
                    myIdCourses.add(course.id)
                    Toast.makeText(context,"Haz adquirido el curso de ${course.name}", Toast.LENGTH_SHORT).show()
                }
                else {
                    Toast.makeText(context,"Ya has adquirido este curso", Toast.LENGTH_SHORT).show()
                }

            }
        }
    }

    private fun courseFromID(courseID: Int): Course? {

        for(course in listCourses) {
            if(course.id == courseID)
                return course
        }
        return null
    }

}