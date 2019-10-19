package au.edu.unimelb.student.mingfengl.data;

import android.content.Context;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.lang.reflect.Array;
import java.util.List;

import au.edu.unimelb.student.mingfengl.R;
import au.edu.unimelb.student.mingfengl.data.model.HistoryData;

public class HistoryAdapter extends BaseQuickAdapter<HistoryData, BaseViewHolder> {
    private Context context;

    public HistoryAdapter(Context context, List<HistoryData> data) {
        super(R.layout.item_history_layout,data);
        this.context = context;
    }


    @Override
    protected void convert(BaseViewHolder helper, HistoryData item) {

    }
}
