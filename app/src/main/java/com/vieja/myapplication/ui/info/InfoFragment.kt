package com.vieja.myapplication.ui.info

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.vieja.bricklist.DBAccess
import com.vieja.myapplication.MainActivity
import com.vieja.myapplication.R

class InfoFragment : Fragment() {

    private lateinit var infoViewModel: InfoViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        infoViewModel =
                ViewModelProvider(this).get(InfoViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_info, container, false)
        val toolbar: Toolbar = root.findViewById<Toolbar>(R.id.toolbar)
        (requireActivity() as? MainActivity)?.setSupportActionBar(toolbar)
        var collapsingToolbar = root.findViewById<CollapsingToolbarLayout>(R.id.collapsingToolbar)
        collapsingToolbar.setExpandedTitleColor(ContextCompat.getColor(requireContext(), R.color.purple_500))

        val dbAccess: DBAccess? = DBAccess.getInstance(requireContext())
        dbAccess!!.open()
        var result = dbAccess.getFirstPet()
        Log.v("DBBB",result)

//        val textView: TextView = root.findViewById(R.id.text_notifications)
//        infoViewModel.text.observe(viewLifecycleOwner, Observer {
//            textView.text = it
//        })
        return root
    }

    override fun onStart() {
        super.onStart()

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_info_edit, menu)
    }



}