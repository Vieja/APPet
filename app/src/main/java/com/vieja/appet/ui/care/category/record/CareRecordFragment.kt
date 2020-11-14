package com.vieja.appet.ui.care.category

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.transition.TransitionInflater
import com.vieja.appet.MainActivity
import com.vieja.appet.R
import kotlinx.android.synthetic.main.fragment_care_category.open_care_category_card
import kotlinx.android.synthetic.main.fragment_record.*

class CareRecordFragment : Fragment(R.layout.fragment_record) {

    private val args: CareRecordFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as? MainActivity)?.setSupportActionBar((toolbar_care_record) as Toolbar)
        setHasOptionsMenu(true)
        val navHostFragment = NavHostFragment.findNavController(this)
        NavigationUI.setupWithNavController((toolbar_care_record) as Toolbar, navHostFragment)
        open_care_category_card.transitionName = "careRecordTransition_" + args.careRecordID
        sharedElementEnterTransition = TransitionInflater.from(context).inflateTransition(android.R.transition.move)


    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_info_edit, menu)
    }


}