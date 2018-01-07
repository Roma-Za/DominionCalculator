package com.zapriy.roman.dominioncalculator.fragments;


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.Gson;
import com.zapriy.roman.dominioncalculator.Consts;
import com.zapriy.roman.dominioncalculator.Helpers.Saves;
import com.zapriy.roman.dominioncalculator.MainActivity;
import com.zapriy.roman.dominioncalculator.R;
import com.zapriy.roman.dominioncalculator.SavesListActivity;


public class StartFragment extends Fragment {

    private View view;
    private SharedPreferences prefs;
    private Activity activity;
    private Saves saves;
    private Gson gson;
    private boolean isDel = true;

    public StartFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_start, container, false);
        activity = getActivity();
        ((MainActivity)activity).hideAB();
        prefs = activity.getPreferences(Activity.MODE_PRIVATE);
        saves = new Saves(activity);
        gson = new Gson();
        Button newBtn = (Button) view.findViewById(R.id.btn_new_start);
        newBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startAddTrophy(Consts.TEMP_NAME);

            }
        });
        Button loadBtn = (Button) view.findViewById(R.id.btn_load_start);
        loadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isDel = false;
                startList();
            }
        });
        Button deleteBtn = (Button) view.findViewById(R.id.btn_delete_start);
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isDel = true;
                startList();
            }
        });
        return view;
    }

    private void startAddTrophy(String accName) {
        Fragment f = new AddTrophyFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Consts.ACCOUNT, accName);
        f.setArguments(bundle);
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.replace(R.id.main_container, f);
        ft.commit();
    }

    private void startList() {
        Intent intent = new Intent(activity, SavesListActivity.class);
        String str = gson.toJson(saves.getSeves());
        intent.putExtra(Consts.SAVES_LIST, str);
        startActivityForResult(intent, Consts.REQUEST_SAVES_LIST);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == Consts.REQUEST_SAVES_LIST) {
            if(resultCode == Activity.RESULT_OK){
                String name = intent.getStringExtra(Consts.NAME_IN_LIST);
                if(isDel){
                    saves.delAccount(name);
                }else{
                    startAddTrophy(name);
                }
                return;
            } else {
                Toast toast = Toast.makeText(activity, R.string.cancel, Toast.LENGTH_SHORT);
                toast.show();
            }

        }
    }

}
