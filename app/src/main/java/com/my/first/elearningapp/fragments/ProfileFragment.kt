package com.my.first.elearningapp.fragments

import android.content.Intent
import android.os.Bundle
import android.transition.Transition
import android.transition.TransitionInflater
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import com.my.first.elearningapp.R
import com.my.first.elearningapp.update.ChangepassActivity
import com.my.first.elearningapp.update.UpdateActivity

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

        // Configura la animación de transición
        val enterTransitionAnim: Transition = TransitionInflater.from(requireContext())
            .inflateTransition(android.R.transition.fade)
        enterTransitionAnim.duration = 1000

        val exitTransitionAnim: Transition = TransitionInflater.from(requireContext())
            .inflateTransition(android.R.transition.fade)
        exitTransitionAnim.duration = 1000

        // Establece la transición de entrada y salida en el Fragment
        enterTransition = enterTransitionAnim
        exitTransition = exitTransitionAnim

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