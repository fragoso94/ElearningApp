package com.my.first.elearningapp.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.Toast
import androidx.lifecycle.coroutineScope
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.my.first.elearningapp.R
import com.my.first.elearningapp.adapter.RecyclerAdapter
import com.my.first.elearningapp.database.ElearningDatabase
import com.my.first.elearningapp.database.entities.UserEntity
import com.my.first.elearningapp.database.utilities.SwipeToDeleteCallback
import com.my.first.elearningapp.databinding.FragmentCourseBinding
import com.my.first.elearningapp.home.DetailActivity
//import com.my.first.elearningapp.model.COURSE_ID
import com.my.first.elearningapp.model.Course
import com.my.first.elearningapp.model.CourseClickListener
import com.my.first.elearningapp.model.listCourses
import com.my.first.elearningapp.update.ChangepassActivity
import com.my.first.elearningapp.update.UpdateActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProfileFragment: Fragment(R.layout.fragment_profile) {

    private lateinit var btnIrAActividad: ImageButton
    private lateinit var btnIrAActividad2: ImageButton
    /*override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }*/

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_profile, container, false)



    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnIrAActividad = view.findViewById(R.id.imageButton3)
        btnIrAActividad2 = view.findViewById(R.id.imageButton4)

        btnIrAActividad.setOnClickListener {

            // Acción al hacer clic en el botón
            val intent = Intent(requireContext(), UpdateActivity::class.java)
            startActivity(intent)



        }

        btnIrAActividad2.setOnClickListener {
            // Acción al hacer clic en el botón

            val intent = Intent(requireContext(), ChangepassActivity::class.java)
            startActivity(intent)



        }
    }

}