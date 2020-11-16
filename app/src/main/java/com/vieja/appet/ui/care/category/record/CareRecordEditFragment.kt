package com.vieja.appet.ui.care.category

import android.app.TimePickerDialog
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.TimePicker
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.core.os.ConfigurationCompat
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.NavigationUI
import androidx.transition.TransitionInflater
import com.google.android.material.datepicker.MaterialDatePicker
import com.vieja.appet.MainActivity
import com.vieja.appet.R
import com.vieja.appet.database.DBAccess
import com.vieja.appet.ui.care.category.record.CareCategoryAdapter
import kotlinx.android.synthetic.main.fragment_care_category.open_care_category_card
import kotlinx.android.synthetic.main.fragment_record.*
import java.text.SimpleDateFormat
import java.util.*


class CareRecordEditFragment : Fragment(R.layout.fragment_record) {

    private val args: CareRecordEditFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as? MainActivity)?.setSupportActionBar((toolbar_care_record) as Toolbar)
        setHasOptionsMenu(true)
        val navHostFragment = NavHostFragment.findNavController(this)
        NavigationUI.setupWithNavController((toolbar_care_record) as Toolbar, navHostFragment)
        open_care_category_card.transitionName = "careRecordTransition_" + args.careRecordID
        sharedElementEnterTransition =
            TransitionInflater.from(context).inflateTransition(android.R.transition.move)

        val builder = MaterialDatePicker.Builder.datePicker()
        val datePicker = builder.build()
        record_date.setOnClickListener {
            datePicker.show(parentFragmentManager, "DATE PICKER")
        }
        val formattedDateFromDatePicker = SimpleDateFormat(
            resources.getString(R.string.datepicker_date), ConfigurationCompat.getLocales(
                resources.configuration
            )[0]
        )
        val formattedDatePretty= SimpleDateFormat(
            resources.getString(R.string.pretty_date), ConfigurationCompat.getLocales(
                resources.configuration
            )[0]
        )
        val formattedTimeFromDatePicker= SimpleDateFormat(
            resources.getString(R.string.timepicker_time), ConfigurationCompat.getLocales(
                resources.configuration
            )[0]
        )
        val formattedTimePretty= SimpleDateFormat(
            resources.getString(R.string.pretty_time), ConfigurationCompat.getLocales(
                resources.configuration
            )[0]
        )
        datePicker.addOnPositiveButtonClickListener {
            val date = formattedDateFromDatePicker.parse(datePicker.headerText)
            record_date.setText(formattedDatePretty.format(date))
        }
        var calendar = Calendar.getInstance()

        // Get the system current hour and minute
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)
        record_time.setOnClickListener {
            val timePickerDialog = TimePickerDialog(requireContext(), { view, h, m ->
                val time = formattedTimeFromDatePicker.parse(h.toString() + ":" + m)
                record_time.setText(formattedTimePretty.format(time))
            },hour,minute,true)
            timePickerDialog.show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_confirm_edit, menu)
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
        (record_text_title as TextView).isFocusableInTouchMode = true
        (record_text_subtitle as TextView).isFocusable = true
        (record_text_subtitle as TextView).isFocusableInTouchMode = true
        (record_text_note as TextView).isFocusable = true
        (record_text_note as TextView).isFocusableInTouchMode = true
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