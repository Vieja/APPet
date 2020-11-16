package com.vieja.bricklist

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.ConfigurationCompat
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.recyclerview.widget.RecyclerView
import com.vieja.appet.R
import com.vieja.appet.models.CareRecord
import com.vieja.appet.ui.care.category.CareCategoryFragmentDirections
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
        val transition = "careRecordTransition_" + holder.id.toString()
        holder.itemView.care_record_card.transitionName = transition
        holder.card.setOnClickListener { view ->
            val extras = FragmentNavigatorExtras(holder.itemView.care_record_card to transition)
            val action = CareCategoryFragmentDirections.actionCareCategoryFragmentToCareRecordFragment(holder.id)
            findNavController(view).navigate(action, extras)
        }
    }

    inner class MainHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title = itemView.record_title
        val subtitle = itemView.record_subtitle
        val date = itemView.record_date
        val hour = itemView.record_hour
        val card = itemView.record_card
        var id : Int = 0

        val formattedDatePretty = SimpleDateFormat(
            context.resources.getString(R.string.pretty_date), ConfigurationCompat.getLocales(
                context.resources.configuration
            )[0]
        )

        val formattedTimePretty = SimpleDateFormat(
            context.resources.getString(R.string.pretty_time), ConfigurationCompat.getLocales(
                context.resources.configuration
            )[0]
        )

        fun bind(pr: CareRecord) {
            title.text = pr.title
            subtitle.text = pr.subtitle
            date.text = formattedDatePretty.format(pr.date)
            hour.text = formattedTimePretty.format(pr.hour!!)
            id = pr.id
        }

    }

}