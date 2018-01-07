package com.zapriy.roman.dominioncalculator.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.zapriy.roman.dominioncalculator.R;
import com.zapriy.roman.dominioncalculator.Entity.Unit;

import java.util.ArrayList;
import java.util.List;

public class ListUnitsAdapter  extends RecyclerView.Adapter {

    private List<Unit> data = new ArrayList<>();
    private View.OnClickListener onClickListener;
    private final Context context;
    public ListUnitsAdapter(Context con, View.OnClickListener listener){
        context = con;
        this.onClickListener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.text_view_row, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView imageAvatar;
        public View mItemContainer;
        public TextView mTextView;
        public ViewHolder(View v) {
            super(v);
            mItemContainer = v.findViewById(R.id.item_container);
            mTextView = (TextView) v.findViewById(R.id.textName);
            imageAvatar = (ImageView)v.findViewById(R.id.imageAvatar);
        }
    }
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder h = (ViewHolder) holder;
        h.mTextView.setText(data.get(position).getName());
        h.mItemContainer.setOnClickListener(onClickListener);
        h.mItemContainer.setTag(position);
        Picasso.with(context)
                .load(data.get(position).getIcon())
                .placeholder(R.drawable.ic_menu_camera)
                .into(h.imageAvatar);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    public List<Unit> getData() {
        return data;
    }

    public void setData(List<Unit> data) {
        this.data = data;
    }
}
