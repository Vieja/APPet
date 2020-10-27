package com.vieja.appet.ui.info

import android.os.Bundle
import android.view.*
import android.widget.TextView
import androidx.appcompat.widget.AppCompatSpinner
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.core.os.ConfigurationCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.vieja.appet.database.DBAccess
import com.vieja.appet.MainActivity
import com.vieja.appet.R
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
        val collapsingToolbar = root.findViewById<CollapsingToolbarLayout>(R.id.collapsingToolbar)
        collapsingToolbar.setExpandedTitleColor(
            ContextCompat.getColor(
                requireContext(),
                R.color.purple_500
            )
        )

        presentPet(root)

        return root
    }

    fun populateDropdownCategory(root: View, categoryName: String) {
        val dbAccess: DBAccess? = DBAccess.getInstance(requireContext())
        dbAccess!!.open()
        val array = dbAccess.getCategories()
        val adapter = CategoryAdapter(requireContext(), array!!)
        val spinner = root.findViewById<AppCompatSpinner>(R.id.spinnerCategory)
        spinner.isEnabled = false
        spinner.adapter = adapter
        spinner.setSelection(array.indexOf(categoryName))
    }

    fun presentPet(root: View) {
        val dbAccess: DBAccess? = DBAccess.getInstance(requireContext())
        dbAccess!!.open()
        val pet = dbAccess.getFirstPet()
        val formattedDateAsLongMonth = SimpleDateFormat(
            "dd MMM yyyy", ConfigurationCompat.getLocales(
                resources.configuration
            )[0]
        )
        root.findViewById<CollapsingToolbarLayout>(R.id.collapsingToolbar).title = pet!!.name

        populateDropdownCategory(root, pet.category)

        if (pet.breed != null) {
            (root.findViewById<TextInputEditText>(R.id.info_text_breed) as TextView).text = pet.breed
        } else {
            root.findViewById<TextInputLayout>(R.id.info_label_breed).visibility = View.GONE
            root.findViewById<TextInputEditText>(R.id.info_text_breed).visibility = View.GONE
        }
        if (pet.species != null) {
            (root.findViewById<TextInputEditText>(R.id.info_text_species) as TextView).text = pet.species
        } else {
            root.findViewById<TextInputLayout>(R.id.info_label_species).visibility = View.GONE
            root.findViewById<TextInputEditText>(R.id.info_text_species).visibility = View.GONE
        }
        if (pet.sex != null) {

            (root.findViewById<TextInputEditText>(R.id.info_text_sex) as TextView).text = pet.getLocalPetSex(
                requireContext()
            )
        } else {
            root.findViewById<TextInputLayout>(R.id.info_label_sex).visibility = View.GONE
            root.findViewById<TextInputEditText>(R.id.info_text_sex).visibility = View.GONE
        }
        if (pet.birth != Date(0)) {
            (root.findViewById<TextInputEditText>(R.id.info_text_birth) as TextView).text = formattedDateAsLongMonth.format(
                pet.birth
            )
        } else {
            root.findViewById<TextInputLayout>(R.id.info_label_birth).visibility = View.GONE
            root.findViewById<TextInputEditText>(R.id.info_text_birth).visibility = View.GONE
        }
        if (pet.death != Date(0)) {
            (root.findViewById<TextInputEditText>(R.id.info_text_death) as TextView).text = formattedDateAsLongMonth.format(
                pet.death
            )
        } else {
            root.findViewById<TextInputLayout>(R.id.info_label_death).visibility = View.GONE
            root.findViewById<TextInputEditText>(R.id.info_text_death).visibility = View.GONE
        }
        if (pet.acquisition_date != Date(0)) {
            (root.findViewById<TextInputEditText>(R.id.info_text_acquisition) as TextView).text = formattedDateAsLongMonth.format(
                pet.acquisition_date
            )
        } else {
            root.findViewById<TextInputLayout>(R.id.info_label_acquisition).visibility = View.GONE
            root.findViewById<TextInputEditText>(R.id.info_text_acquisition).visibility = View.GONE
        }
        if (pet.color != null) {
            (root.findViewById<TextInputEditText>(R.id.info_text_color) as TextView).text = pet.color
        } else {
            root.findViewById<TextInputLayout>(R.id.info_label_color).visibility = View.GONE
            root.findViewById<TextInputEditText>(R.id.info_text_color).visibility = View.GONE
        }
//        if (pet.description != null) {
//            (root.findViewById<TextInputEditText>(R.id.info_text_description) as TextView).text = pet.description
//        } else {
//            root.findViewById<TextInputLayout>(R.id.info_label_description).visibility = View.GONE
//            root.findViewById<TextInputEditText>(R.id.info_text_color).visibility = View.GONE
//        }
        if (pet.genes != null) {
            (root.findViewById<TextInputEditText>(R.id.info_text_genes) as TextView).text = pet.genes
        } else {
            root.findViewById<TextInputLayout>(R.id.info_label_genes).visibility = View.GONE
            root.findViewById<TextInputEditText>(R.id.info_text_genes).visibility = View.GONE
        }

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_info_edit, menu)
    }



}