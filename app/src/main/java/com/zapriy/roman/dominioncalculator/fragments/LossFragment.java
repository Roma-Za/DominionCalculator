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
import android.widget.TextView;

import com.zapriy.roman.dominioncalculator.ChoiceUnitActivity;
import com.zapriy.roman.dominioncalculator.Consts;
import com.zapriy.roman.dominioncalculator.Entity.AmountUnits;
import com.zapriy.roman.dominioncalculator.Entity.ArrUnits;
import com.zapriy.roman.dominioncalculator.Entity.Unit;
import com.zapriy.roman.dominioncalculator.Helpers.AccountHelper;
import com.zapriy.roman.dominioncalculator.MainActivity;
import com.zapriy.roman.dominioncalculator.R;
import com.zapriy.roman.dominioncalculator.adapters.AddTrophyAdapter;

import java.util.ArrayList;
import java.util.List;

public class LossFragment extends Fragment implements View.OnClickListener {

    private View view;
    private Activity activity;
    private AccountHelper helper;
    private ArrUnits units;
    private AddTrophyAdapter addTrophyAdapter;
    private List<AmountUnits> dataUnits;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView mRecyclerView;
    private long sum = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_loss, container, false);
        view.findViewById(R.id.btnAddLoss).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAddUnit();
            }
        });
        activity = getActivity();
        helper = ((MainActivity) activity).getAccountHelper();
        ((MainActivity) activity).showAB();
        units = new ArrUnits(helper.getAcIsUseMeat());
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_loss);
        mLayoutManager = new LinearLayoutManager(activity);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setHasFixedSize(true);

        dataUnits = new ArrayList<>();
        addTrophyAdapter = new AddTrophyAdapter(activity, this);
        setData();

        view.findViewById(R.id.btnCalcLoss).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calcLoss();
            }
        });
        TextView tv = (TextView)view.findViewById(R.id.textResLoss);
        tv.setText(activity.getString(R.string.resource_balance) + " " + helper.getSumm());
        ((MainActivity) activity).getSupportActionBar().setTitle(R.string.calc_loss);
        return view;
    }

    private void setText() {
        View view = this.getView().findViewById(R.id.textResLoss);
        ((TextView)view).setText(activity.getString(R.string.resource_balance) + " " + helper.getSumm());
    }
    private void calcLoss() {
        for (AmountUnits au : dataUnits) {
            helper.logDifUnits(au);
            sum+=au.getSum();
        }
        helper.difToSumm(sum);
        dataUnits.clear();
        sum = 0;
        setData();
        setText();
    }

    private void setData() {
        if (dataUnits != null) {
            addTrophyAdapter.setData(dataUnits);
        }
        mRecyclerView.setAdapter(addTrophyAdapter);
        addTrophyAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        int idx = (Integer) v.getTag();
        dataUnits.remove(idx);
        setData();
    }

    private void openAddUnit() {
        Intent intent = new Intent(getActivity(), ChoiceUnitActivity.class);
        intent.putExtra("isUseMeat", helper.getAcIsUseMeat());
        startActivityForResult(intent, Consts.REQUEST_UNIT);
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
    }
}
