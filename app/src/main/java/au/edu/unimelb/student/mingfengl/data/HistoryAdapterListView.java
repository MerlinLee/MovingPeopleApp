package au.edu.unimelb.student.mingfengl.data;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;
import java.util.Map;

import au.edu.unimelb.student.mingfengl.data.model.HistoryData;

public class HistoryAdapterListView extends BaseAdapter {
    private List<HistoryData> data;
    private LayoutInflater layoutInflater;
    private Context context;

    public HistoryAdapterListView(List<HistoryData> data, Context context) {
        this.data = data;
        this.context = context;
        this.layoutInflater=LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        return null;
    }
}
