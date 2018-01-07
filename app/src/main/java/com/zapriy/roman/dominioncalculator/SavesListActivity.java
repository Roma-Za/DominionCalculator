package com.zapriy.roman.dominioncalculator;

import android.content.Intent;
import android.support.constraint.solver.SolverVariable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zapriy.roman.dominioncalculator.adapters.SaveListAdapter;

import java.lang.reflect.Type;
import java.util.List;

public class SavesListActivity extends AppCompatActivity implements View.OnClickListener{

    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView mRecyclerView;
    private SaveListAdapter mAdapter;
    private Type itemsListType = new TypeToken<List<String>>() {}.getType();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saves_list);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view_saves);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new SaveListAdapter(this);
        Intent intent = getIntent();
        String list = intent.getStringExtra(Consts.SAVES_LIST);
        List<String> listItemsDes = new Gson().fromJson(list, itemsListType);
        if (listItemsDes != null) {
            mAdapter.setData(listItemsDes);
        }
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        String str = (String) v.getTag();
        Intent intent = new Intent();
        intent.putExtra(Consts.NAME_IN_LIST, str);
        setResult(RESULT_OK, intent);
        finish();
    }
}
