package com.vieja.appet.ui.care.category

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.transition.TransitionInflater
import com.vieja.appet.MainActivity
import com.vieja.appet.R
import com.vieja.appet.database.DBAccess
import com.vieja.appet.ui.care.category.record.CareCategoryAdapter
import com.vieja.appet.ui.info.CategoryAdapter
import kotlinx.android.synthetic.main.fragment_care_category.open_care_category_card
import kotlinx.android.synthetic.main.fragment_info.*
import kotlinx.android.synthetic.main.fragment_record.*

class CareRecordEditFragment : Fragment(R.layout.fragment_record) {

    private val args: CareRecordEditFragmentArgs by navArgs()

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
        presentRecord()
        makeViewsEditable()
    }

    private fun populateDropdownCategory(categoryName: String) {
        val dbAccess: DBAccess? = DBAccess.getInstance(requireContext())
        dbAccess!!.open()
        val array = dbAccess.getCareCategories()
        val adapter = CareCategoryAdapter(requireContext(), array!!)
        val spinner = spinnerCareRecord
        spinner.isEnabled = false
        spinner.adapter = adapter
        var id = 0
        for (cat in array) {
            if (cat.res_name == categoryName) break
            else id += 1
        }
    }

    private fun makeViewsEditable() {
        (record_text_title as TextView).isFocusable = true
    }

    private fun presentRecord() {
        val dbAccess: DBAccess? = DBAccess.getInstance(requireContext())
        dbAccess!!.open()
        val record = dbAccess.getRecord(args.careRecordID)
        populateDropdownCategory(record!!.category)
        (record_text_title as TextView).text = record.title
    }


}