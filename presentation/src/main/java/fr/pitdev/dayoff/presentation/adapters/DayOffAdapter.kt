package fr.pitdev.dayoff.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import extensions.isToday
import fr.pitdev.dayoff.domain.models.DayOff
import fr.pitdev.dayoff.presentation.databinding.ItemDayoffBinding
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.*


class DayOffAdapter : ListAdapter<DayOff, DayOffAdapter.DayOffViewHolder>(DiffCallback) {


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DayOffViewHolder {
        return DayOffViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: DayOffViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class DayOffViewHolder(private val binding: ItemDayoffBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(dayOff: DayOff) {

            binding.root.isSelected = dayOff.date.isToday()
            binding.dayoffDate.text = dayOff.date.format(
                DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL).withLocale(
                    Locale.getDefault()
                )
            )
            binding.dayoffName.text = dayOff.name
        }


        companion object {
            fun from(parent: ViewGroup): DayOffViewHolder {
                return DayOffViewHolder(
                    ItemDayoffBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
        }
    }


    companion object DiffCallback : DiffUtil.ItemCallback<DayOff>() {
        override fun areItemsTheSame(oldItem: DayOff, newItem: DayOff): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: DayOff, newItem: DayOff): Boolean {
            return oldItem == newItem && newItem.date.isToday() && !newItem.date.isToday()
        }

    }
}
