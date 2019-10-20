package au.edu.unimelb.student.mingfengl.ui.login.ui.history

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import au.edu.unimelb.student.mingfengl.R
import au.edu.unimelb.student.mingfengl.data.HistoriesAdapter
import au.edu.unimelb.student.mingfengl.data.ServerResponse
import au.edu.unimelb.student.mingfengl.data.model.HistoryData
import com.alibaba.fastjson.JSON
import com.github.salomonbrys.kotson.fromJson
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class HistoryFragment : Fragment() {
    private lateinit var recyclerViewHistoryData:RecyclerView
    private lateinit var refreshLayout:SwipeRefreshLayout
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_history, container, false)
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
        var msg = "{\n" +
                "  \"code\": 0,\n" +
                "  \"errmsg\": [\n" +
                "    {\n" +
                "      \"count\": 0,\n" +
                "      \"history_id\": 1,\n" +
                "      \"location\": \"http://45.113.234.163:8009/mingfeng/output/storage_emulated_0_DCIM_Camera_VID_20191019_135843-2019_10_19-025842.mp4\",\n" +
                "      \"name\": \"/storage/emulated/0/DCIM/Camera/VID_20191019_135843.mp4\",\n" +
                "      \"status\": 1,\n" +
                "      \"submit_time\": \"2019/10/19, 03:01\",\n" +
                "      \"user_id\": 2,\n" +
                "      \"video_id\": 1\n" +
                "    },\n" +
                "    {\n" +
                "      \"count\": 0,\n" +
                "      \"history_id\": 2,\n" +
                "      \"location\": \"http://45.113.234.163:8009/mingfeng/output/storage_emulated_0_DCIM_Camera_VID_20191019_135902-2019_10_19-025900.mp4\",\n" +
                "      \"name\": \"/storage/emulated/0/DCIM/Camera/VID_20191019_135902.mp4\",\n" +
                "      \"status\": 1,\n" +
                "      \"submit_time\": \"2019/10/19, 03:02\",\n" +
                "      \"user_id\": 2,\n" +
                "      \"video_id\": 2\n" +
                "    },\n" +
                "    {\n" +
                "      \"count\": 0,\n" +
                "      \"history_id\": 3,\n" +
                "      \"location\": \"http://45.113.234.163:8009/mingfeng/output/storage_emulated_0_DCIM_Camera_VID_20191019_135916-2019_10_19-025920.mp4\",\n" +
                "      \"name\": \"/storage/emulated/0/DCIM/Camera/VID_20191019_135916.mp4\",\n" +
                "      \"status\": 1,\n" +
                "      \"submit_time\": \"2019/10/19, 03:03\",\n" +
                "      \"user_id\": 2,\n" +
                "      \"video_id\": 3\n" +
                "    },\n" +
                "    {\n" +
                "      \"count\": 0,\n" +
                "      \"history_id\": 4,\n" +
                "      \"location\": \"http://45.113.234.163:8009/mingfeng/output/storage_emulated_0_DCIM_Camera_VID_20191019_154436-2019_10_19-044434.mp4\",\n" +
                "      \"name\": \"/storage/emulated/0/DCIM/Camera/VID_20191019_154436.mp4\",\n" +
                "      \"status\": 1,\n" +
                "      \"submit_time\": \"2019/10/19, 04:45\",\n" +
                "      \"user_id\": 2,\n" +
                "      \"video_id\": 4\n" +
                "    },\n" +
                "    {\n" +
                "      \"count\": 1,\n" +
                "      \"history_id\": 5,\n" +
                "      \"location\": \"http://45.113.234.163:8009/mingfeng/output/storage_emulated_0_DCIM_Camera_VID_20191019_203438-2019_10_19-093437.mp4\",\n" +
                "      \"name\": \"/storage/emulated/0/DCIM/Camera/VID_20191019_203438.mp4\",\n" +
                "      \"status\": 1,\n" +
                "      \"submit_time\": \"2019/10/19, 09:35\",\n" +
                "      \"user_id\": 2,\n" +
                "      \"video_id\": 5\n" +
                "    }\n" +
                "  ]\n" +
                "}"
        try {
            var errmsg = JSON.parseObject(msg,ServerResponse::class.java)
            var list = JSON.parseArray(errmsg.errmsg,HistoryData::class.java)
            return list
        }catch (e:Exception){
            Log.e("JSON",e.printStackTrace().toString())
        }
        var errmsg = JSON.parseObject(msg,ServerResponse::class.java)
        var list = JSON.parseArray(errmsg.errmsg,HistoryData::class.java)
        return list
    }


}