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


// The custom adapter for the Dare ListView
public class DareListViewAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<String> dares;
    private GameMaker gm;

    public DareListViewAdapter(GameMaker gm, Context context) {
        this.gm = gm;
        this.context = context;
        dares = gm.getDareList();
    }


    public int getCount() {
        return dares.size();
    }

    @Override
    public Object getItem(int i) {
        return dares.get(i);
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
        dareListText.setText(dares.get(i));
        Button deleteBtn = view.findViewById(R.id.delete_question_delete_btn);
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dares.remove(i);
                gm.updateDareDB(dares);
                notifyDataSetChanged();
            }
        });

        return view;
    }
}
