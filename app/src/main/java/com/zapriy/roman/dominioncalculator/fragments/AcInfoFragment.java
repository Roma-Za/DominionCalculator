package com.zapriy.roman.dominioncalculator.fragments;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.zapriy.roman.dominioncalculator.Consts;
import com.zapriy.roman.dominioncalculator.Helpers.AccountHelper;
import com.zapriy.roman.dominioncalculator.MainActivity;
import com.zapriy.roman.dominioncalculator.R;

import java.util.ArrayList;

public class AcInfoFragment extends Fragment {
    private AccountHelper helper;
    private Activity activity;
    private TextView tvInfo;
    private TextView tvOpenHistory;
    private TextView tvHistory;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        activity = getActivity();
        View view = inflater.inflate(R.layout.fragment_ac_info, container, false);
        tvInfo = (TextView) view.findViewById(R.id.tv_ac_info_fragment);
        String str = getArguments().getString(Consts.AC_INFO);
        helper = ((MainActivity)activity).getAccountHelper(str);
        tvInfo.setText(helper.toString());
        tvOpenHistory = (TextView) view.findViewById(R.id.open_text_history);
        tvHistory = (TextView) view.findViewById(R.id.history_text);
        tvHistory.setText(getStrLogs(helper.getLogs()));
        tvOpenHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tvHistory.getVisibility()==View.VISIBLE){
                    tvHistory.setVisibility(View.GONE);
                    tvOpenHistory.setText("+");
                }else {
                    tvHistory.setVisibility(View.VISIBLE);
                    tvOpenHistory.setText("-");
                }
            }
        });
        return view;
    }

    private String getStrLogs(ArrayList<String> logs) {
        if(logs.isEmpty()) return "";
        StringBuffer stringBuffer = new StringBuffer();
        for (String str : logs) {
            stringBuffer.append(str);
            stringBuffer.append("\n");
        }
        return stringBuffer.toString();
    }
}
