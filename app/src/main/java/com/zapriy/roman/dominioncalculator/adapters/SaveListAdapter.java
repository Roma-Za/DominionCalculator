package com.zapriy.roman.dominioncalculator.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.zapriy.roman.dominioncalculator.R;

import java.util.ArrayList;
import java.util.List;

public class SaveListAdapter  extends RecyclerView.Adapter{

    private List<String> data = new ArrayList<>();
    private View.OnClickListener onClickListener;

    public SaveListAdapter(View.OnClickListener listener){
        onClickListener = listener;


    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.text_row, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public View mItemContainer;
        public TextView mTextView;
        public ViewHolder(View v) {
            super(v);
            mItemContainer = v.findViewById(R.id.item_container_saves);
            mTextView = (TextView) v.findViewById(R.id.textNameSaves);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder h = (ViewHolder) holder;
        h.mTextView.setText(data.get(position));
        h.mItemContainer.setOnClickListener(onClickListener);
        h.mItemContainer.setTag(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public List<String> getData() {
        return data;
    }

    public void setData(List<String> data) {
        this.data = data;
    }
}
