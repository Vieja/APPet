package com.vieja.bricklist

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.recyclerview.widget.RecyclerView
import com.vieja.appet.R
import com.vieja.appet.models.CareCategory
import com.vieja.appet.ui.care.CareFragmentDirections
import kotlinx.android.synthetic.main.care_category_card.view.*

class CareCategoryListAdapter(private val context: Context, private val caregoriesList: List<CareCategory>) :
    RecyclerView.Adapter<CareCategoryListAdapter.MainHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = MainHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.care_category_card, parent, false)
    )

    override fun getItemCount() = caregoriesList.size

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        holder.bind(caregoriesList[position])



        holder.name.setOnClickListener { view ->
//            val emailCardDetailTransitionName = "transition_test"
//            val extras = FragmentNavigatorExtras(view to emailCardDetailTransitionName)
            val action = CareFragmentDirections.actionNavigationCareToCareCategoryFragment(view.careCategoryName.text.toString())
            findNavController(view).navigate(action)//, null, null, extras)
        }
    }

    inner class MainHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name = itemView.careCategoryName
        var id: Int = 0

        fun bind(pr: CareCategory) {
            name.text = pr.name
            id = pr.id
        }

    }

}