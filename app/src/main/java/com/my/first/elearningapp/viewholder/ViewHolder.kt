package com.my.first.elearningapp.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.my.first.elearningapp.databinding.CardviewCourseBinding
import com.my.first.elearningapp.model.Course
import com.my.first.elearningapp.model.CourseClickListener

class ViewHolder(private val cardviewCourseBinding: CardviewCourseBinding,
                 private val onClickListener:(Course) -> Unit //private val clickListener: CourseClickListener
)
    : RecyclerView.ViewHolder(cardviewCourseBinding.root){

    fun bindCourse(course: Course){
        cardviewCourseBinding.courseImage.setImageResource(course.image)
        cardviewCourseBinding.tvCourse.text = course.name
        cardviewCourseBinding.tvPrice.text = "$" + String.format("%.2f", course.price)
        cardviewCourseBinding.tvHours.text = " "
        cardviewCourseBinding.rbCalification.rating = course.rating

        cardviewCourseBinding.cardviewLayout.setOnClickListener{
            //clickListener.onClick(course)
            onClickListener(course)
        }
    }
}

