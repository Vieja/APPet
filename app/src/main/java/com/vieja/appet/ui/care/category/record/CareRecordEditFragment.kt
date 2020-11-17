package com.vieja.appet.ui.care.category

import android.app.TimePickerDialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
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
import com.vieja.appet.models.CareCategory
import com.vieja.appet.ui.care.category.record.CareCategoryAdapter
import kotlinx.android.synthetic.main.care_record_card.*
import kotlinx.android.synthetic.main.fragment_care_category.open_care_category_card
import kotlinx.android.synthetic.main.fragment_record.*
import kotlinx.android.synthetic.main.fragment_record.record_date
import java.text.SimpleDateFormat
import java.util.*


class CareRecordEditFragment : Fragment(R.layout.fragment_record) {

    private val args: CareRecordEditFragmentArgs by navArgs()
    lateinit var navController : NavController
    lateinit var care_categories : ArrayList<CareCategory>

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
        populateDropdownCategory()
        if (args.careRecordID != 0) presentRecord()
        makeViewsEditable()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        var id = 0
        if (item.itemId == R.id.menuConfirm) {
            if(isNotNull()) {
                if (args.careRecordID != 0) {
                    updateRecord()
                    id = args.careRecordID
                } else {
                    id = 1
                }
                requireActivity().onBackPressed()
            }

        }

        return super.onOptionsItemSelected(item)
    }

    private fun isNotNull(): Boolean {
        var result = true
        if ((record_text_title as TextView).text.toString() == "") {
            record_text_title.error = getString(R.string.error_title_null)
            result = false
        } else record_text_title.error = null
        if ((record_date as TextView).text.toString() == "") {
            record_date.error = getString(R.string.error_date_null)
            result = false
        } else record_date.error = null

        return result
    }

    private fun updateRecord() {
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

        val date = formattedDatePretty.parse(record_date.text.toString())
        val time = formattedTimePretty.parse(record_time.text.toString())

        if (time != null)
            dbAccess.updateRecord(
                args.careRecordID,
                care_categories.get(spinnerCareRecord.selectedItemId.toInt()).res_name,
                (record_text_title as TextView).text.toString(),
                (record_text_subtitle as TextView).text.toString(),
                date,
                time,
                (record_text_note as TextView).text.toString()
            )
        else
            dbAccess.updateRecord(
                args.careRecordID,
                care_categories.get(spinnerCareRecord.selectedItemId.toInt()).res_name,
                (record_text_title as TextView).text.toString(),
                (record_text_subtitle as TextView).text.toString(),
                date,
                (record_text_note as TextView).text.toString()
            )
    }

    private fun populateDropdownCategory() {
        val dbAccess: DBAccess? = DBAccess.getInstance(requireContext())
        dbAccess!!.open()
        care_categories = dbAccess.getCareCategories()
        val adapter = CareCategoryAdapter(requireContext(), care_categories!!)
        val spinner = spinnerCareRecord
        spinner.adapter = adapter
        var id = 0
        for (cat in care_categories) {
            if (cat.res_name == args.careCategoryName) break
            else id += 1
        }
        spinner.setSelection(id)

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
        (record_text_title as TextView).text = record!!.title
        (record_text_subtitle as TextView).text = record.subtitle
        (record_text_note as TextView).text = record.note
        (record_date as TextView).text = formattedDatePretty.format(record.date)
        (record_time as TextView).text = formattedTimePretty.format(record.hour)
    }


}