package com.zapriy.roman.dominioncalculator.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.zapriy.roman.dominioncalculator.Entity.AmountUnits;
import com.zapriy.roman.dominioncalculator.R;

import java.util.ArrayList;
import java.util.List;

public class AddTrophyAdapter extends RecyclerView.Adapter {

    private List<AmountUnits> data = new ArrayList<>();
    private View.OnClickListener onClickListener;
    private final Context context;

    public AddTrophyAdapter(Context con, View.OnClickListener listener){
        context = con;
        this.onClickListener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_item, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView imageUnit;
        public View mItemContainer;
        public TextView mTextView;
        public TextView mTVAmount;
        public Button mButtonDel;
        public ViewHolder(View v) {
            super(v);
            mItemContainer = v.findViewById(R.id.item_container_trophy);
            mTextView = (TextView) v.findViewById(R.id.nameUnit);
            mTVAmount = (TextView) v.findViewById(R.id.amount);
            imageUnit = (ImageView)v.findViewById(R.id.imageUnit);
            mButtonDel = (Button)v.findViewById(R.id.del);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder h = (ViewHolder) holder;
        h.mTextView.setText(data.get(position).getUnit().getName());
        h.mTVAmount.setText(String.format("%d", data.get(position).getAmt()));
        h.mButtonDel.setOnClickListener(onClickListener);
        h.mButtonDel.setTag(position);
        Picasso.with(context)
                .load(data.get(position).getUnit().getIcon())
                .placeholder(R.drawable.ic_menu_camera)
                .into(h.imageUnit);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    public List<AmountUnits> getData() {
        return data;
    }

    public void setData(List<AmountUnits> data) {
        this.data = data;
    }
}
