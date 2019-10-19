package au.edu.unimelb.student.mingfengl.ui.login.ui.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import au.edu.unimelb.student.mingfengl.R
import au.edu.unimelb.student.mingfengl.data.HistoriesAdapter
import au.edu.unimelb.student.mingfengl.data.model.HistoryData


class HistoryFragment : Fragment() {
    companion object {
//        private val DATASET_COUNT = 60
    }
    private lateinit var recyclerViewHistoryData:RecyclerView
    private lateinit var refreshLayout:SwipeRefreshLayout
//    private lateinit var recyclerView: RecyclerView
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_history, container, false)
//        var histories = arrayOf("Merlin","Tom","Cino","Frank")
//        var listView = root.findViewById<ListView>(R.id.history)
//        var lisViewAdapter = this.activity?.let { ArrayAdapter<String>(it,android.R.layout.simple_list_item_1,histories) }
//        listView.adapter = lisViewAdapter
        refreshLayout = root.findViewById(R.id.history_refreshLayout)
        recyclerViewHistoryData = root.findViewById(R.id.recyclerViewHistory)
        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        refreshLayout.setOnRefreshListener {
            showHistories()
        }
    }

    private fun showHistories(){
        refreshLayout.isRefreshing = true
        recyclerViewHistoryData.layoutManager = LinearLayoutManager(activity)
        recyclerViewHistoryData.adapter = HistoriesAdapter(fakeHistories())
        refreshLayout.isRefreshing = false
    }

    private fun fakeHistories():List<HistoryData>{
        var history1 = HistoryData("19/10/2019","17:15",1,"/app/ha","/app/fake")
        var history2 = HistoryData("19/10/2019","17:15",2,"/app/ha","/app/fake")
        var history3 = HistoryData("19/10/2019","17:15",3,"/app/ha","/app/fake")
        return listOf(history1,history2,history3)
    }
}