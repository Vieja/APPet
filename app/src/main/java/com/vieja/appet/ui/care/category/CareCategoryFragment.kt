package com.vieja.appet.ui.care.category

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.transition.TransitionInflater
import com.vieja.appet.MainActivity
import com.vieja.appet.R
import com.vieja.appet.database.DBAccess
import com.vieja.bricklist.CareCategoryListAdapter
import com.vieja.bricklist.CareRecordListAdapter
import kotlinx.android.synthetic.main.care_category_card.*
import kotlinx.android.synthetic.main.fragment_care.*
import kotlinx.android.synthetic.main.fragment_care_category.*
import kotlinx.android.synthetic.main.fragment_care_category.open_care_category_card
import kotlinx.android.synthetic.main.fragment_record.*

class CareCategoryFragment : Fragment(R.layout.fragment_care_category) {

    private val args: CareCategoryFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        postponeEnterTransition()
        view.doOnPreDraw { startPostponedEnterTransition() }
        (requireActivity() as? MainActivity)?.setSupportActionBar((toolbar_care_category) as Toolbar)
        val navHostFragment = NavHostFragment.findNavController(this)
        NavigationUI.setupWithNavController((toolbar_care_category) as Toolbar, navHostFragment)
        open_care_category_card.transitionName = "careCategoryTransition_" + args.careCategoryName
        sharedElementEnterTransition = TransitionInflater.from(context).inflateTransition(android.R.transition.move)

        inflateRecyclerView()

    }

    private fun inflateRecyclerView() {
        val dbAccess: DBAccess? = DBAccess.getInstance(requireContext())
        dbAccess!!.open()
        val categoriesList = dbAccess.getCareRecords(args.careCategoryName, args.petId)
        recordRecyclerView.setHasFixedSize(true)
        recordRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = CareRecordListAdapter(requireContext(), categoriesList)
        }
    }

}