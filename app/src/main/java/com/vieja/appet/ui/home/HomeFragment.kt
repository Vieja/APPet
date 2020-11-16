package com.vieja.appet.ui.home

import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.vieja.appet.MainActivity
import com.vieja.appet.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.fragment_home.*


class HomeFragment : Fragment(R.layout.fragment_home) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as MainActivity).setSupportActionBar(toolbar_home)
        val appBarConfiguration = (activity as MainActivity).appBarConfiguration
        val navHostFragment = NavHostFragment.findNavController(this)
        NavigationUI.setupWithNavController(toolbar_home, navHostFragment, appBarConfiguration)
        toolbar_home.navigationIcon = ResourcesCompat.getDrawable(resources, R.drawable.ic_baseline_menu_white_24, null)
        toolbar_home.setNavigationOnClickListener {
            (activity as MainActivity).drawer_layout.openDrawer(GravityCompat.START)
        }

    }


}