package com.zapriy.roman.dominioncalculator.Helpers;

import android.app.Activity;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.zapriy.roman.dominioncalculator.Consts;
import com.zapriy.roman.dominioncalculator.Entity.Account;
import com.zapriy.roman.dominioncalculator.Entity.AmountUnits;
import com.zapriy.roman.dominioncalculator.Entity.Unit;
import com.zapriy.roman.dominioncalculator.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class AccountHelper {
    private Activity activity;
    private Account account;
    private SharedPreferences prefs;
    private Calendar calendar;
    private Gson gson;

    public AccountHelper(Activity act, String acc) {
        activity = act;
        gson = new Gson();
        prefs = activity.getPreferences(Activity.MODE_PRIVATE);

        if (acc.equals(Consts.TEMP_NAME)) {
            account = new Account();
        } else {
            String acStr = prefs.getString(acc, null);
            if (acStr != null) {
                account = gson.fromJson(acStr, Account.class);
            } else {
                Toast.makeText(activity, acc + activity.getString(R.string.not_exist), Toast.LENGTH_LONG);
            }
        }
        calendar = Calendar.getInstance();
    }

    public void setAcName(String name) {
        account.name = name;
        addLog(activity.getString(R.string.set )+ " " + activity.getString(R.string.name), name);
    }

    public void setAcIsUseMeat(boolean isUseMeat) {
        account.isUseMeat = isUseMeat;
        addLog(activity.getString(R.string.set )+ " " + activity.getString(R.string.use_meat),
                isUseMeat ? activity.getString(R.string.yes) : activity.getString(R.string.no));
    }

    public void setPercent(int percent){
        account.additionalPercent = percent;
        addLog(activity.getString(R.string.set )+ " " + activity.getString(R.string.percent), " " + percent + " %");
    }

    public String getAcName() {
        return account.name;
    }

    public boolean getAcIsUseMeat() {
        return account.isUseMeat;
    }

    private void addLog(String type, String text) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String strLog = dateFormat.format(calendar.getTime()) + " " + type + ": " + text + "\n";
        Log.e("strLog", strLog);
        account.logs.add(strLog);
    }

    public ArrayList<String> getLogs() {
        return account.logs;
    }

    public void addToSumm(long summ){
        long percent = 0;
        if(this.account.additionalPercent > 0) {
            percent = summ * this.account.additionalPercent / 100;
        } else {
            percent = 0;
        }
        this.account.summ+=summ;
        this.account.summ+=percent;
        addLog(activity.getString(R.string.add_to_summ), String.format("%d", summ) + " "
                + activity.getString(R.string.and_additional_percent) + " " + percent + " "
                + activity.getString(R.string.summ) + " = " + account.summ);
    }

    public void logAddUnits(AmountUnits units){
        addLog(activity.getString(R.string.addUnits), activity.getString(units.getUnit().getName()) + " " + units.getAmt());
    }

    public void difToSumm(long summ){
        this.account.summ-=summ;
        addLog(activity.getString(R.string.dif_to_summ), String.format("%d", summ) + " " + activity.getString(R.string.summ) + " = " + account.summ);
    }

    public void logDifUnits(AmountUnits units){
        addLog(activity.getString(R.string.difUnits), activity.getString(units.getUnit().getName()) + " " + units.getAmt());
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(activity.getString(R.string.name));
        sb.append(": ");
        sb.append(account.name);
        sb.append('\n');
        sb.append(activity.getString(R.string.use_meat));
        sb.append(": ");
        if (account.isUseMeat == true)
            sb.append(activity.getString(R.string.yes));
        else if (account.isUseMeat == false)
            sb.append(activity.getString(R.string.no));
        else
            sb.append(" __");
        sb.append('\n');
        sb.append(activity.getString(R.string.percent));
        sb.append(": ");
        sb.append(account.additionalPercent);
        sb.append("%");
        sb.append('\n');
        sb.append(activity.getString(R.string.summ));
        sb.append(": ");
        sb.append(account.summ);

        return sb.toString();
    }

    public String getAccGSON(){
        return gson.toJson(account);
    }

    public long getSumm(){
        return account.summ;
    }

}
