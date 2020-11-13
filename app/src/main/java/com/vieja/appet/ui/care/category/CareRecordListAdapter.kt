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
import androidx.core.os.ConfigurationCompat
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.recyclerview.widget.RecyclerView
import com.vieja.appet.R
import com.vieja.appet.models.CareCategory
import com.vieja.appet.models.CareRecord
import com.vieja.appet.ui.care.CareFragmentDirections
import kotlinx.android.synthetic.main.care_category_card.view.*
import kotlinx.android.synthetic.main.care_record_card.view.*
import java.text.SimpleDateFormat

class CareRecordListAdapter(private val context: Context, private val recordsList: List<CareRecord>) :
    RecyclerView.Adapter<CareRecordListAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = MainHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.care_record_card, parent, false)
    )

    override fun getItemCount() = recordsList.size

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        holder.bind(recordsList[position])
//        val transition = "careCategoryTransition_" + holder.name.text.toString()
//        holder.itemView.care_category_card.transitionName = transition
//        holder.name.setOnClickListener { view ->
//            val extras = FragmentNavigatorExtras(holder.itemView.care_category_card to transition)
//            val action = CareFragmentDirections.actionNavigationCareToCareCategoryFragment(view.careCategoryName.text.toString())
//            findNavController(view).navigate(action, extras)
//        }
    }

    inner class MainHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title = itemView.record_title
        val subtitle = itemView.record_subtitle
        val date = itemView.record_date
        val hour = itemView.record_hour

        val formattedDateAsLongMonth = SimpleDateFormat(
            "dd MMM yyyy", ConfigurationCompat.getLocales(
                context.resources.configuration
            )[0]
        )

        val formattedDateAsTime = SimpleDateFormat(
            "HH:mm", ConfigurationCompat.getLocales(
                context.resources.configuration
            )[0]
        )

        fun bind(pr: CareRecord) {
            title.text = pr.title
            subtitle.text = pr.subtitle
            date.text = formattedDateAsLongMonth.format(pr.date!!)
            hour.text = formattedDateAsTime.format(pr.hour!!)
        }

    }

}