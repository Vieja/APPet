package com.vieja.appet.ui.info

import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.os.ConfigurationCompat
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.vieja.appet.MainActivity
import com.vieja.appet.MainActivityViewModel
import com.vieja.appet.R
import com.vieja.appet.database.DBAccess
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.collapsing_toolbar.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_info.*
import java.text.SimpleDateFormat
import java.util.*

class InfoFragment : Fragment(R.layout.fragment_info) {

    private lateinit var mainViewModel: MainActivityViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val imm = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(requireView().getWindowToken(), 0)
        mainViewModel = ViewModelProvider(requireActivity()).get(MainActivityViewModel::class.java)
        (activity as MainActivity).setSupportActionBar(toolbar_collapsed)
        setHasOptionsMenu(true)
        val appBarConfiguration = (activity as MainActivity).appBarConfiguration
        val navHostFragment = NavHostFragment.findNavController(this)
        NavigationUI.setupWithNavController(toolbar_collapsed, navHostFragment, appBarConfiguration)
        toolbar_collapsed.navigationIcon = ResourcesCompat.getDrawable(resources, R.drawable.ic_baseline_menu_white_24, null)
        toolbar_collapsed.setNavigationOnClickListener {
            (activity as MainActivity).drawer_layout.openDrawer(GravityCompat.START)
        }

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
            val formattedDatePretty = SimpleDateFormat(
                resources.getString(R.string.pretty_date), ConfigurationCompat.getLocales(
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
                (info_text_sex as TextView).text = pet.sex
            } else {
                info_label_sex.visibility = View.GONE
                info_text_sex.visibility = View.GONE
            }
            if (pet.birth != Date(0)) {
                (info_text_birth as TextView).text =
                    formattedDatePretty.format(
                        pet.birth!!
                    )
            } else {
                info_label_birth.visibility = View.GONE
                info_text_birth.visibility = View.GONE
            }
            if (pet.death != Date(0)) {
                (info_text_death as TextView).text =
                    formattedDatePretty.format(
                        pet.death!!
                    )
            } else {
                info_label_death.visibility = View.GONE
                info_text_death.visibility = View.GONE
            }
            if (pet.acquisition_date != Date(0)) {
                (info_text_acquisition as TextView).text =
                    formattedDatePretty.format(
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