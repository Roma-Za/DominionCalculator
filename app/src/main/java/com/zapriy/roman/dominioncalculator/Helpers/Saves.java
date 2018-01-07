package com.zapriy.roman.dominioncalculator.Helpers;

import android.app.Activity;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.zapriy.roman.dominioncalculator.Consts;

import java.util.ArrayList;
import java.util.Collections;

public class Saves {
    private SharedPreferences prefs;

    public Saves(Activity activity) {
        prefs = activity.getPreferences(Activity.MODE_PRIVATE);
    }

    public ArrayList<String> getSeves() {
        ArrayList arr = new ArrayList();
        if (prefs.contains(Consts.ACCOUNTS)) {
            String s = prefs.getString(Consts.ACCOUNTS, "");
            Collections.addAll(arr, TextUtils.split(s, ","));
            return arr;
        } else {

            return arr;
        }
    }

    public String getAccountStr(String name){
        return prefs.getString(name, null);
    }

    public void delAccount(String name){
        prefs.edit().remove(name).commit();
        ArrayList<String> temp = new ArrayList<>();
        for (String item: getSeves()) {
            if(!item.equals(name)){
                temp.add(item);
            }
        }
        setAccounts(temp);
    }
    
    private void setAccounts(ArrayList<String> acc) {
        String s = TextUtils.join(",", acc);
        prefs.edit().putString(Consts.ACCOUNTS, s).commit();
    }

    public void saveAcc(String name, String acc){
        if (!prefs.contains(Consts.ACCOUNTS)) {
            prefs.edit().putString(Consts.ACCOUNTS, name).commit();
        } else {
            boolean flag = false;
            ArrayList<String> temp = getSeves();
            for (String i : temp) {
                if (i.compareTo(name) == 0) {
                    flag = true;
                }
            }

            if (!flag) {
                temp.add(name);
                setAccounts(temp);
            }
        }
        prefs.edit().putString(name, acc).commit();
    }

}
