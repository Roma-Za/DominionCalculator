package com.zapriy.roman.dominioncalculator.fragments;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.zapriy.roman.dominioncalculator.ChoiceUnitActivity;
import com.zapriy.roman.dominioncalculator.Consts;
import com.zapriy.roman.dominioncalculator.Entity.AmountUnits;
import com.zapriy.roman.dominioncalculator.Entity.ArrUnits;
import com.zapriy.roman.dominioncalculator.Entity.Unit;
import com.zapriy.roman.dominioncalculator.Helpers.AccountHelper;
import com.zapriy.roman.dominioncalculator.MainActivity;
import com.zapriy.roman.dominioncalculator.R;
import com.zapriy.roman.dominioncalculator.UnitActivity;
import com.zapriy.roman.dominioncalculator.adapters.AddTrophyAdapter;

import java.util.ArrayList;
import java.util.List;


public class PreviewFragment extends Fragment implements View.OnClickListener {

    private View view;
    private Activity activity;
    private AccountHelper helper;
    private ArrUnits units;
    private AddTrophyAdapter addTrophyAdapter;
    private List<AmountUnits> dataUnits;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView mRecyclerView;
    private long sum = 0;
    private ImageView imageAvatar;
    private TextView tvRes;
    private int idxSingle;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_preview, container, false);
        view.findViewById(R.id.btnAddPreview).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAddUnit();
            }
        });
        activity = getActivity();
        helper = ((MainActivity) activity).getAccountHelper();
        ((MainActivity) activity).showAB();
        units = new ArrUnits(helper.getAcIsUseMeat());
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_preview);
        mLayoutManager = new LinearLayoutManager(activity);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setHasFixedSize(true);

        dataUnits = new ArrayList<>();
        addTrophyAdapter = new AddTrophyAdapter(activity, this);
        setData();

        view.findViewById(R.id.btnCalcPreview).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calcPreview();
            }
        });
        imageAvatar = (ImageView)view.findViewById(R.id.imageAvatarPrew);
        idxSingle = Consts.IDX_GOLD;
        setImage(idxSingle);
        imageAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), UnitActivity.class);
                intent.putExtra("isUseMeat", helper.getAcIsUseMeat());
                startActivityForResult(intent, Consts.REQUEST_UNIT_SINGLE);
            }
        });
        TextView tvRes = (TextView)view.findViewById(R.id.textResources);
        tvRes.setText(" " + helper.getSumm());
        return view;
    }

    private void setImage(int idx) {
        Picasso.with(activity)
                .load(units.getUnits().get(idx).getIcon())
                .placeholder(R.drawable.ic_menu_camera)
                .into(imageAvatar);
    }

    private void calcPreview() {
        for (AmountUnits au : dataUnits) {
            sum+=au.getSum();
        }
        long realSumm = helper.getSumm();
        long summPreview = realSumm - sum;
        sum = 0;
        long amount = summPreview/units.getUnits().get(idxSingle).getCost();
        String text = " " + amount;
        setText(text);

    }

    private void setText(String text) {
        View view = this.getView().findViewById(R.id.textResources);
        ((TextView)view).setText(text);
    }

    private void setData() {
        if (dataUnits != null) {
            addTrophyAdapter.setData(dataUnits);
        }
        mRecyclerView.setAdapter(addTrophyAdapter);
        addTrophyAdapter.notifyDataSetChanged();
    }

    private void openAddUnit() {
        Intent intent = new Intent(getActivity(), ChoiceUnitActivity.class);
        intent.putExtra("isUseMeat", helper.getAcIsUseMeat());
        startActivityForResult(intent, Consts.REQUEST_UNIT);
    }

    @Override
    public void onClick(View v) {
        int idx = (Integer) v.getTag();
        dataUnits.remove(idx);
        setData();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent){
        if (requestCode == Consts.REQUEST_UNIT) {
            if (resultCode == Activity.RESULT_OK) {
                int idx = intent.getIntExtra(Consts.UNIT_IDX, 0);
                int amount = intent.getIntExtra(Consts.AMOUNT, 0);
                Unit unit = units.getUnits().get(idx);
                dataUnits.add(new AmountUnits(unit, amount));
                setData();
                return;
            }
        }
        if(requestCode == Consts.REQUEST_UNIT_SINGLE) {
            if (resultCode == Activity.RESULT_OK) {
                idxSingle = intent.getIntExtra(Consts.UNIT_IDX, 0);
                setImage(idxSingle);
                setText(" ");
                setData();
                return;
            }
        }
    }
}
