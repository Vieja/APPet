package com.vieja.bricklist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.recyclerview.widget.RecyclerView
import com.vieja.appet.R
import com.vieja.appet.models.CareCategory
import com.vieja.appet.ui.care.CareFragmentDirections
import kotlinx.android.synthetic.main.care_category_card.view.*

class CareCategoryListAdapter(private val caregoriesList: List<CareCategory>) :
    RecyclerView.Adapter<CareCategoryListAdapter.MainHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = MainHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.care_category_card, parent, false)
    )

    override fun getItemCount() = caregoriesList.size

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        holder.bind(caregoriesList[position])
        val transition = "careCategoryTransition_" + holder.category_name.toString()
        holder.itemView.care_category_card.transitionName = transition
        holder.name.setOnClickListener { view ->
            val extras = FragmentNavigatorExtras(holder.itemView.care_category_card to transition)
            val action = CareFragmentDirections.actionNavigationCareToCareCategoryFragment(holder.category_name)
            findNavController(view).navigate(action, extras)
        }
    }

    inner class MainHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name = itemView.careCategoryName
        val counter = itemView.careCategoryCount
        lateinit var category_name : String
        fun bind(pr: CareCategory) {
            name.text = pr.local_name
            counter.text = pr.records_count.toString()
            category_name = pr.res_name
        }

    }

}