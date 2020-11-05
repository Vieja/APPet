package com.vieja.appet.ui.care

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.appcompat.widget.AppCompatSpinner
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.appbar.AppBarLayout
import com.vieja.appet.MainActivity
import com.vieja.appet.MainActivityViewModel
import com.vieja.appet.R
import com.vieja.appet.database.DBAccess
import com.vieja.appet.models.Pet
import com.vieja.appet.ui.info.CategoryAdapter
import com.vieja.appet.ui.info.PetAdapter

class CareFragment : Fragment(), AdapterView.OnItemSelectedListener {

    private lateinit var mainViewModel: MainActivityViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        mainViewModel = ViewModelProvider(requireActivity()).get(MainActivityViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_care, container, false)
        val toolbar: Toolbar = root.findViewById<Toolbar>(R.id.filter_toolbar)
        (requireActivity() as? MainActivity)?.setSupportActionBar(toolbar)

        inflateSpinner(root)

        return root
    }

    fun inflateSpinner(root: View) {
        val dbAccess: DBAccess? = DBAccess.getInstance(requireContext())
        dbAccess!!.open()
        val array = dbAccess.getPets()
        val adapter = PetAdapter(requireContext(), array!!)
        val spinner = root.findViewById<AppCompatSpinner>(R.id.spinnerPet)
        spinner.adapter = adapter
        mainViewModel.getChosenPet().observe(viewLifecycleOwner, Observer {
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