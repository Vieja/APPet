package com.vieja.appet.ui.care.category

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.vieja.appet.MainActivity
import com.vieja.appet.R
import kotlinx.android.synthetic.main.fragment_care_category.*

class CareCategoryFragment : Fragment(R.layout.fragment_care_category) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as? MainActivity)?.setSupportActionBar(toolbar_care_category)
        val navHostFragment = NavHostFragment.findNavController(this)
        NavigationUI.setupWithNavController(toolbar_care_category, navHostFragment)
    }


}