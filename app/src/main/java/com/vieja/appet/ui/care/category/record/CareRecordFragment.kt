package com.vieja.appet.ui.care.category

import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.core.os.ConfigurationCompat
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.NavigationUI
import androidx.transition.TransitionInflater
import com.vieja.appet.MainActivity
import com.vieja.appet.R
import com.vieja.appet.database.DBAccess
import com.vieja.appet.ui.care.category.record.CareCategoryAdapter
import kotlinx.android.synthetic.main.fragment_care_category.open_care_category_card
import kotlinx.android.synthetic.main.fragment_record.*
import java.text.SimpleDateFormat

class CareRecordFragment : Fragment(R.layout.fragment_record) {

    private val args: CareRecordFragmentArgs by navArgs()
    lateinit var navController : NavController

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val imm = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(requireView().getWindowToken(), 0)
        navController = Navigation.findNavController(view)
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
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menuEdit) {
            val action = CareRecordFragmentDirections.actionCareRecordFragmentToCareRecordEditFragment(args.careRecordID, args.careCategoryName)
            navController.navigate(action)
        }

        return super.onOptionsItemSelected(item)
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
        spinner.setSelection(id)
    }

    private fun presentRecord() {
        val dbAccess: DBAccess? = DBAccess.getInstance(requireContext())
        dbAccess!!.open()

        val formattedDatePretty= SimpleDateFormat(
            resources.getString(R.string.pretty_date), ConfigurationCompat.getLocales(
                resources.configuration
            )[0]
        )

        val formattedTimePretty= SimpleDateFormat(
            resources.getString(R.string.pretty_time), ConfigurationCompat.getLocales(
                resources.configuration
            )[0]
        )

        val record = dbAccess.getRecord(args.careRecordID)
        populateDropdownCategory(record!!.category)
        (record_text_title as TextView).text = record.title
        (record_text_subtitle as TextView).text = record.subtitle
        (record_text_note as TextView).text = record.note
        (record_date as TextView).text = formattedDatePretty.format(record.date)
        (record_time as TextView).text = formattedTimePretty.format(record.hour)
    }


}