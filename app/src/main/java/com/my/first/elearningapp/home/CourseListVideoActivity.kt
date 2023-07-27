package com.my.first.elearningapp.home

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.my.first.elearningapp.R
import com.my.first.elearningapp.databinding.ActivityCourseListVideoBinding
import android.widget.MediaController

class CourseListVideoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCourseListVideoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCourseListVideoBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        //setContentView(R.layout.activity_course_list_video)

        val pathVideo = "android.resource://" + applicationContext.packageName + "/" // + R.raw.Curso1
        initVideoUI(pathVideo, view)
    }

    private fun initVideoUI(path: String, view: View){
        with(binding){
            val mediaController1 = MediaController(view.context)
            val mediaController2 = MediaController(view.context)
            val mediaController3 = MediaController(view.context)

            mediaController1.setAnchorView(videoView1) // R.raw.Curso1
            mediaController2.setAnchorView(videoView2) // R.raw.Curso1
            mediaController3.setAnchorView(videoView3) // R.raw.Curso1

            videoView1.setVideoURI(Uri.parse(path + R.raw.curso1))
            videoView1.setMediaController(mediaController1)
            videoView1.requestFocus()
            fbPlay1.setOnClickListener {
                videoView1.start()
            }
//            videoView1.setOnPreparedListener {
//                it.start()
//            }

            videoView2.setVideoURI(Uri.parse(path + R.raw.curso2))
            videoView2.setMediaController(mediaController2)
            videoView2.requestFocus()
            fbPlay2.setOnClickListener {
                videoView2.start()
            }
//            videoView2.setOnPreparedListener {
//                it.stop()
//            }

            videoView3.setVideoURI(Uri.parse(path + R.raw.curso3))
            videoView3.setMediaController(mediaController3)
            videoView3.requestFocus()
            fbPlay3.setOnClickListener {
                videoView3.start()
            }
//            videoView3.setOnPreparedListener {
//                it.stop()
//            }


        }

    }

}