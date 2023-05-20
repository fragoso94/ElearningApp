package com.my.first.elearningapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.my.first.elearningapp.databinding.CardviewCourseBinding
import com.my.first.elearningapp.model.Course
import com.my.first.elearningapp.model.CourseClickListener
import com.my.first.elearningapp.viewholder.ViewHolder

class RecyclerAdapter(private var courses: List<Course>,
                      private val onClickListener:(Course) -> Unit //private val clickListener: CourseClickListener
)
    : RecyclerView.Adapter<ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
        val binding = CardviewCourseBinding.inflate(view, parent, false)
        return ViewHolder(binding, onClickListener) //clickListener
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindCourse(courses[position])
    }

    override fun getItemCount(): Int = courses.size
}

