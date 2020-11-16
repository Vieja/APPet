package com.vieja.appet.ui.care.category

import android.content.Context
import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.widget.Toolbar
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.transition.TransitionInflater
import com.vieja.appet.MainActivity
import com.vieja.appet.MainActivityViewModel
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
    private lateinit var mainViewModel: MainActivityViewModel
    lateinit var navController : NavController

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val imm = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(requireView().getWindowToken(), 0)
        navController = Navigation.findNavController(view)
        mainViewModel = ViewModelProvider(requireActivity()).get(MainActivityViewModel::class.java)
        postponeEnterTransition()
        view.doOnPreDraw { startPostponedEnterTransition() }
        (requireActivity() as? MainActivity)?.setSupportActionBar((toolbar_care_category) as Toolbar)
        val navHostFragment = NavHostFragment.findNavController(this)
        NavigationUI.setupWithNavController((toolbar_care_category) as Toolbar, navHostFragment)
        open_care_category_card.transitionName = "careCategoryTransition_" + args.careCategoryName
        sharedElementEnterTransition = TransitionInflater.from(context).inflateTransition(android.R.transition.move)

        inflateRecyclerView()

        floating_action_button.setOnClickListener {
            val action = CareCategoryFragmentDirections.actionCareCategoryFragmentToCareRecordEditFragment()
            navController.navigate(action)
        }

    }

    private fun inflateRecyclerView() {
        val dbAccess: DBAccess? = DBAccess.getInstance(requireContext())
        dbAccess!!.open()
        mainViewModel.getChosenPet().observe(viewLifecycleOwner, {
            val categoriesList = dbAccess.getCareRecords(args.careCategoryName, it)
            if (categoriesList.size != 0) {
                infoNoRecords.visibility = View.GONE
                recordRecyclerView.setHasFixedSize(true)
                recordRecyclerView.apply {
                    layoutManager = LinearLayoutManager(requireContext())
                    adapter = CareRecordListAdapter(requireContext(), categoriesList)
                }
            }
        })
    }

}