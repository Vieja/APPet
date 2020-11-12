package com.vieja.bricklist

import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
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
//            holder.itemView.caretransitionName = transition
            Log.v("HEHEholdername",holder.name.text.toString())
            val transition = "careCategoryTransition_" + holder.name.text.toString()
            holder.itemView.care_category_card.transitionName = transition
            Log.v("HEHEwczesniej",holder.itemView.care_category_card.transitionName)
            val extras = FragmentNavigatorExtras(holder.itemView.care_category_card to transition)
            val action = CareFragmentDirections.actionNavigationCareToCareCategoryFragment(view.careCategoryName.text.toString())
            findNavController(view).navigate(action, extras)
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