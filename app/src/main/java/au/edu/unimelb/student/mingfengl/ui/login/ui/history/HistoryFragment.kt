package au.edu.unimelb.student.mingfengl.ui.login.ui.history

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.ListFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import au.edu.unimelb.student.mingfengl.R
import au.edu.unimelb.student.mingfengl.data.HistoryAdapter
import au.edu.unimelb.student.mingfengl.data.HistoryAdapterListView
import au.edu.unimelb.student.mingfengl.data.model.HistoryData
import au.edu.unimelb.student.mingfengl.services.GlobalApplication
import java.util.*


class HistoryFragment : Fragment() {
    companion object {
//        private val DATASET_COUNT = 60
    }

//    private lateinit var recyclerView: RecyclerView
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_history, container, false)
        var histories = arrayOf("Merlin","Tom","Cino","Frank")
        var listView = root.findViewById<ListView>(R.id.history)
        var lisViewAdapter = this.activity?.let { ArrayAdapter<String>(it,android.R.layout.simple_list_item_1,histories) }
        listView.adapter = lisViewAdapter
        return root
    }


}