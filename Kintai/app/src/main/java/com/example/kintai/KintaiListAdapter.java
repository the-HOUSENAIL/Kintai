package com.example.kintai;

import android.app.Activity;
import android.content.Context;
import android.graphics.Movie;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.ArrayList;
import java.util.List;

public class KintaiListAdapter extends BaseAdapter {

    private Context context = null;
    private List<KintaiData> data = null;
    private int resource = 0;

    public KintaiListAdapter(Context context, List<KintaiData> data, int resource) {
        this.context = context;
        this.data = data;
        this.resource = resource;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return data.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Activity activity = (Activity) context;
        KintaiData kintaiData = (KintaiData) this.getItem(position);
        ConstraintLayout layout = (ConstraintLayout) activity.getLayoutInflater().inflate(resource, null);
        TextView startDay = layout.findViewById(R.id.dateList);
        TextView startTime = layout.findViewById(R.id.startTime);
        TextView endTime = layout.findViewById(R.id.endTime);
        TextView breakTime = layout.findViewById(R.id.breakTime);
        TextView totalWork = layout.findViewById(R.id.totalWork);

        startDay.setText(kintaiData.getStartDay());
        startTime.setText(kintaiData.getStartTime());
        endTime.setText(kintaiData.getEndTime());
        breakTime.setText(kintaiData.getBreakTime());
        totalWork.setText(kintaiData.getTotalWork());

        return layout;
    }
}
