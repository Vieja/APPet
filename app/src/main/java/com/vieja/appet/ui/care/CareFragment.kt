package com.vieja.appet.ui.care

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.vieja.appet.MainActivity
import com.vieja.appet.MainActivityViewModel
import com.vieja.appet.R
import com.vieja.appet.database.DBAccess
import com.vieja.appet.models.Pet
import com.vieja.appet.ui.info.PetAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.collapsing_toolbar.*
import kotlinx.android.synthetic.main.fragment_care.*

class CareFragment : Fragment(R.layout.fragment_care), AdapterView.OnItemSelectedListener {

    private lateinit var mainViewModel: MainActivityViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainViewModel = ViewModelProvider(requireActivity()).get(MainActivityViewModel::class.java)
        (requireActivity() as? MainActivity)?.setSupportActionBar((filter_toolbar) as Toolbar)
        val appBarConfiguration = AppBarConfiguration(setOf(
            R.id.navigation_home, R.id.navigation_care, R.id.navigation_info))
        val navHostFragment = NavHostFragment.findNavController(this);
        NavigationUI.setupWithNavController((filter_toolbar) as Toolbar, navHostFragment,appBarConfiguration)


        inflateSpinner()

        test.setOnClickListener {
            findNavController(view).navigate(R.id.action_navigation_care_to_careCategoryFragment)
        }
    }

    private fun inflateSpinner() {
        val dbAccess: DBAccess? = DBAccess.getInstance(requireContext())
        dbAccess!!.open()
        val array = dbAccess.getPets()
        val adapter = PetAdapter(requireContext(), array!!)
        val spinner = spinnerPet
        spinner.adapter = adapter
        mainViewModel.getChosenPet().observe(viewLifecycleOwner, {
            var id = 0
            for (pet in array) {
                if (it == pet.id) {
                    break
                } else id++
            }
            spinner.setSelection(id)
        })

        spinner.onItemSelectedListener = this
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        val pet = parent?.getItemAtPosition(position) as Pet
        mainViewModel.choosePet(pet.id)
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {

    }


}