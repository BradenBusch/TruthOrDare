package com.example.truthordare;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import java.util.ArrayList;


// The custom adapter for the Truth ListView
public class TruthListViewAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<String> truths;
    private GameMaker gm;

    public TruthListViewAdapter (GameMaker gm, Context context) {
        this.context = context;
        this.gm = gm;
        this.truths = gm.getTruthList();
    }


    public int getCount() {
        return truths.size();
    }

    @Override
    public Object getItem(int i) {
        return truths.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int i, View convertView, ViewGroup viewGroup) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.custom_listview, null);
        }
        TextView dareListText = view.findViewById(R.id.list_item_string);
        dareListText.setText(truths.get(i));
        Button deleteBtn = view.findViewById(R.id.delete_question_delete_btn);
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                truths.remove(i);
                gm.updateTruthDB(truths);
                notifyDataSetChanged();
            }
        });

        return view;
    }
}
