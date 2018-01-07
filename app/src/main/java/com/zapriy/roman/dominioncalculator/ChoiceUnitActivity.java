package com.zapriy.roman.dominioncalculator;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.zapriy.roman.dominioncalculator.Entity.ArrUnits;
import com.zapriy.roman.dominioncalculator.adapters.ListUnitsAdapter;

public class ChoiceUnitActivity extends AppCompatActivity implements View.OnClickListener{

    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView mRecyclerView;
    private ListUnitsAdapter mAdapter;
    private Boolean isUse;
    private int idx;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choice_unit);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new ListUnitsAdapter(this, this);
        Intent intent = getIntent();

        isUse = intent.getBooleanExtra("isUseMeat", false);
        ArrUnits items = new ArrUnits(isUse);
        if (items != null) {
            mAdapter.setData(items.getUnits());
        }
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        idx = (Integer) v.getTag();
        Intent intent = new Intent(this, InputActivity.class);
        intent.putExtra(Consts.UNIT_IDX, idx);
        intent.putExtra("isUseMeat", isUse);
        startActivityForResult(intent, Consts.REQUEST_UNIT);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
            if (resultCode == Activity.RESULT_OK) {
                int amount = intent.getIntExtra(Consts.AMOUNT, 0);
                Intent intent2 = new Intent();
                intent2.putExtra(Consts.UNIT_IDX, idx);
                intent2.putExtra(Consts.AMOUNT, amount);
                setResult(RESULT_OK, intent2);
                finish();
            }
    }
}
