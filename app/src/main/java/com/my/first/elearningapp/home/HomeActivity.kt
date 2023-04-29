package com.my.first.elearningapp.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.my.first.elearningapp.fragments.ProfileFragment
import com.my.first.elearningapp.R
import com.my.first.elearningapp.fragments.CourseFragment
import com.my.first.elearningapp.fragments.HomeFragment

class HomeActivity : AppCompatActivity() {
    val homeFragment: Fragment = HomeFragment()
    val courseFragment: Fragment = CourseFragment()
    val profileFragment: Fragment = ProfileFragment()

    lateinit var navigation: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        initUI()
        initListener()
        loadFragment(homeFragment) //lo primero que cargará es el primer fragmento
    }

    private fun initUI(){
        navigation = findViewById(R.id.bottom_navigation)
    }

    private fun initListener(){
        navigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.homeFragment -> {
                    loadFragment(homeFragment)
                }
                R.id.courseFragment -> {
                    loadFragment(courseFragment)
                }
                R.id.profileFragment -> {
                    loadFragment(profileFragment)
                }
            }
            true
        }
    }

    private fun loadFragment(fragment: Fragment){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.frame_container, fragment)
        transaction.commit()
    }
}