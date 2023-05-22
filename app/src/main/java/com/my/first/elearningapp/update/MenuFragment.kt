package com.my.first.elearningapp.update
import com.my.first.elearningapp.update.ChangepassActivity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.my.first.elearningapp.R

class MenuFragment : Fragment(R.layout.fragment_profile ) {

    private lateinit var btnIrAActividad: ImageButton

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnIrAActividad = view.findViewById(R.id.imageButton88)
        btnIrAActividad.setOnClickListener {
            // Acción al hacer clic en el botón
            val intent = Intent(requireContext(), ChangepassActivity::class.java)
            startActivity(intent)
            Toast.makeText(requireContext(), "¡Botón pulsado!", Toast.LENGTH_SHORT).show()


        }


    }















}










