package au.edu.unimelb.student.mingfengl.data

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import au.edu.unimelb.student.mingfengl.R
import au.edu.unimelb.student.mingfengl.data.model.HistoryData
import kotlinx.android.synthetic.main.item_history_layout.view.*

class HistoriesAdapter (val histories:List<HistoryData>):RecyclerView.Adapter<HistoriesAdapter.HistoryViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        return HistoryViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_history_layout,parent,false)
        )
    }

    override fun getItemCount() = histories.size

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {

            val history = histories[position]
            holder.view.h_time.text = history.time
            holder.view.h_count.text = history.count.toString()
            holder.view.h_download.text = history.download
            holder.view.h_delete.text = history.delete
            holder.view.h_date.text = history.date

    }

    class HistoryViewHolder(val view: View):RecyclerView.ViewHolder(view)
}