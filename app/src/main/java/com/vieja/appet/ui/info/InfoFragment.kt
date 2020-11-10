package com.vieja.appet.ui.info

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.os.ConfigurationCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.vieja.appet.MainActivity
import com.vieja.appet.MainActivityViewModel
import com.vieja.appet.R
import com.vieja.appet.database.DBAccess
import kotlinx.android.synthetic.main.collapsing_toolbar.*
import kotlinx.android.synthetic.main.fragment_info.*
import java.text.SimpleDateFormat
import java.util.*

class InfoFragment : Fragment(R.layout.fragment_info) {

    private lateinit var mainViewModel: MainActivityViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainViewModel = ViewModelProvider(requireActivity()).get(MainActivityViewModel::class.java)
        (requireActivity() as? MainActivity)?.setSupportActionBar(toolbar_collapsed)
        setHasOptionsMenu(true)
        val appBarConfiguration = AppBarConfiguration(setOf(
            R.id.navigation_home, R.id.navigation_care, R.id.navigation_info))
        val navHostFragment = NavHostFragment.findNavController(this);
        NavigationUI.setupWithNavController(toolbar_collapsed, navHostFragment,appBarConfiguration)

        collapsingToolbar.setExpandedTitleColor(
            ContextCompat.getColor(
                requireContext(),
                R.color.purple_500
            )
        )
        presentPet()
    }

    private fun populateDropdownCategory(categoryName: String) {
        val dbAccess: DBAccess? = DBAccess.getInstance(requireContext())
        dbAccess!!.open()
        val array = dbAccess.getCategories()
        val adapter = CategoryAdapter(requireContext(), array!!)
        val spinner = spinnerCategory
        spinner.isEnabled = false
        spinner.adapter = adapter
        var id = 0
        for (cat in array) {
            if (cat.res_name == categoryName) break
            else id += 1
        }
        spinner.setSelection(id)
    }

    private fun presentPet() {
        val dbAccess: DBAccess? = DBAccess.getInstance(requireContext())
        dbAccess!!.open()
        mainViewModel.getChosenPet().observe(viewLifecycleOwner, {
            val pet = dbAccess.getPet(it)
            val formattedDateAsLongMonth = SimpleDateFormat(
                "dd MMM yyyy", ConfigurationCompat.getLocales(
                    resources.configuration
                )[0]
            )
            collapsingToolbar.title = pet!!.name

            populateDropdownCategory(pet.category)

            if (pet.breed != null) {
                (info_text_breed as TextView).text = pet.breed
            } else {
                info_label_breed.visibility = View.GONE
                info_text_breed.visibility = View.GONE
            }
            if (pet.species != null) {
                (info_text_species as TextView).text = pet.species
            } else {
                info_label_species.visibility = View.GONE
                info_text_species.visibility = View.GONE
            }
            if (pet.sex != null) {
                (info_text_sex as TextView).text =
                    pet.getLocalPetSex(requireContext())
            } else {
                info_label_sex.visibility = View.GONE
                info_text_sex.visibility = View.GONE
            }
            if (pet.birth != Date(0)) {
                (info_text_birth as TextView).text =
                    formattedDateAsLongMonth.format(
                        pet.birth!!
                    )
            } else {
                info_label_birth.visibility = View.GONE
                info_text_birth.visibility = View.GONE
            }
            if (pet.death != Date(0)) {
                (info_text_death as TextView).text =
                    formattedDateAsLongMonth.format(
                        pet.death!!
                    )
            } else {
                info_label_death.visibility = View.GONE
                info_text_death.visibility = View.GONE
            }
            if (pet.acquisition_date != Date(0)) {
                (info_text_acquisition as TextView).text =
                    formattedDateAsLongMonth.format(
                        pet.acquisition_date!!
                    )
            } else {
                info_label_acquisition.visibility =
                    View.GONE
                info_text_acquisition.visibility =
                    View.GONE
            }
            if (pet.color != null) {
                (info_text_color as TextView).text =
                    pet.color
            } else {
                info_label_color.visibility = View.GONE
                info_text_color.visibility = View.GONE
            }
            if (pet.genes != null) {
                (info_text_genes as TextView).text =
                    pet.genes
            } else {
                info_label_genes.visibility = View.GONE
                info_text_genes.visibility = View.GONE
            }
        })

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_info_edit, menu)
    }


}