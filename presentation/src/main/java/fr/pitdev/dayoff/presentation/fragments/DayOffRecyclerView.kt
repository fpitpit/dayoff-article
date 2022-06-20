package fr.pitdev.dayoff.presentation.fragments

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import fr.pitdev.dayoff.domain.models.DayOff
import fr.pitdev.dayoff.presentation.adapters.DayOffAdapter

class DayOffRecyclerView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : RecyclerView(context, attrs) {

    init {
        adapter = DayOffAdapter()
        layoutManager = LinearLayoutManager(context)
    }

    fun setData(data: List<DayOff>) {
        (adapter as DayOffAdapter).submitList(data)
    }

}