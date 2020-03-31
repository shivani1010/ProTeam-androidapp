package com.android.proteam;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class RecordAdapter extends BaseAdapter {

    private static class RecordViewHolder {

        public TextView stateNameView;
        public TextView countConfirmedView;
        public TextView countQuarantineView;
    }

    private Context recordContext;
    private List<Record> recordList;

    public RecordAdapter(Context context, List<Record> records) {
        recordList = records;
        recordContext = context;
    }

    @Override
    public int getCount() {
        return recordList.size();
    }

    @Override
    public Object getItem(int position) {
        return recordList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        RecordViewHolder holder;

        if (view == null) {
            LayoutInflater recordInflater = (LayoutInflater)
                    recordContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            view = recordInflater.inflate(R.layout.record, null);

            holder = new RecordViewHolder();
            holder.stateNameView = (TextView) view.findViewById(R.id.state_name);
            holder.countConfirmedView = (TextView) view.findViewById(R.id.count_confirmed);
            holder.countQuarantineView = (TextView) view.findViewById(R.id.count_quarantined);
            view.setTag(holder);

        } else {
            holder = (RecordViewHolder) view.getTag();
        }

        Record record = (Record) getItem(i);
        holder.stateNameView.setText(record.stateName);
        holder.countConfirmedView.setText(record.countConfirmed);
        holder.countQuarantineView.setText(record.countQuarantine);
        return view;
    }
    public void add(Record record) {
        recordList.add(record);
        notifyDataSetChanged();
    }
}

