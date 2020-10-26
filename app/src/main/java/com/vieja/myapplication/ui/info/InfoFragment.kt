package com.vieja.myapplication.ui.info

import android.opengl.Visibility
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.core.os.ConfigurationCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.vieja.bricklist.DBAccess
import com.vieja.myapplication.MainActivity
import com.vieja.myapplication.R
import java.text.SimpleDateFormat
import java.util.*

class InfoFragment : Fragment() {

    private lateinit var infoViewModel: InfoViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        infoViewModel =
                ViewModelProvider(this).get(InfoViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_info, container, false)
        val toolbar: Toolbar = root.findViewById<Toolbar>(R.id.toolbar)
        (requireActivity() as? MainActivity)?.setSupportActionBar(toolbar)
        var collapsingToolbar = root.findViewById<CollapsingToolbarLayout>(R.id.collapsingToolbar)
        collapsingToolbar.setExpandedTitleColor(ContextCompat.getColor(requireContext(), R.color.purple_500))

        presentPet(root)

        return root
    }

    fun presentPet(root: View) {
        val dbAccess: DBAccess? = DBAccess.getInstance(requireContext())
        dbAccess!!.open()
        val pet = dbAccess.getFirstPet()
        if (pet != null) Log.v("DBBB", pet.category)
        root.findViewById<CollapsingToolbarLayout>(R.id.collapsingToolbar).title = pet!!.name
        when (pet!!.category) {
            "dog" -> {
                (root.findViewById<TextInputEditText>(R.id.info_text_category) as TextView).text = pet.category
                root.findViewById<TextInputLayout>(R.id.info_label_species_breed).hint = "Rasa"
                (root.findViewById<TextInputEditText>(R.id.info_text_species_breed) as TextView).text = pet.breed
                (root.findViewById<TextInputEditText>(R.id.info_text_sex) as TextView).text = pet.sex
                val formattedDateAsLongMonth = SimpleDateFormat("dd MMM yyyy", ConfigurationCompat.getLocales(resources.configuration)[0])
                if (pet.birth!= Date(0))
                    (root.findViewById<TextInputEditText>(R.id.info_text_birth) as TextView).text = formattedDateAsLongMonth.format(pet.birth)
                else {
                    root.findViewById<TextInputLayout>(R.id.info_label_birth).visibility = View.GONE
                    root.findViewById<TextInputEditText>(R.id.info_text_birth).visibility = View.GONE
                }
                if (pet.acquisition_date!= Date(0))
                    (root.findViewById<TextInputEditText>(R.id.info_text_acquisition) as TextView).text = formattedDateAsLongMonth.format(pet.acquisition_date)
                else {
                    root.findViewById<TextInputLayout>(R.id.info_label_acquisition).visibility = View.GONE
                    root.findViewById<TextInputEditText>(R.id.info_text_acquisition).visibility = View.GONE
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_info_edit, menu)
    }



}