package com.zapriy.roman.dominioncalculator;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.zapriy.roman.dominioncalculator.Entity.ArrUnits;
import com.zapriy.roman.dominioncalculator.adapters.ListUnitsAdapter;

public class UnitActivity extends AppCompatActivity implements View.OnClickListener{

    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView mRecyclerView;
    private ListUnitsAdapter mAdapter;
    private Boolean isUse;

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
        int idx = (Integer) v.getTag();
        Intent intent2 = new Intent();
        intent2.putExtra(Consts.UNIT_IDX, idx);
        setResult(RESULT_OK, intent2);
        finish();
    }
}
