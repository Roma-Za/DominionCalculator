package com.zapriy.roman.dominioncalculator;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.zapriy.roman.dominioncalculator.Helpers.AccountHelper;
import com.zapriy.roman.dominioncalculator.Helpers.Saves;
import com.zapriy.roman.dominioncalculator.fragments.AcInfoFragment;
import com.zapriy.roman.dominioncalculator.fragments.AddTrophyFragment;
import com.zapriy.roman.dominioncalculator.fragments.LossFragment;
import com.zapriy.roman.dominioncalculator.fragments.PreviewFragment;
import com.zapriy.roman.dominioncalculator.fragments.StartFragment;

import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private AccountHelper accountHelper;
    private Saves saves;
    private int itemIdx = 0;
    private static long back_pressed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        saves = new Saves(this);

        goStartFragment();
    }

    public void goStartFragment() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_container, new StartFragment())
                .commit();
    }

    public void hideAB() {
        getSupportActionBar().hide();
    }

    public void showAB() {
        getSupportActionBar().show();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (back_pressed + 2000 > System.currentTimeMillis())
                super.onBackPressed();
            else
                Toast.makeText(getBaseContext(), this.getString(R.string.press_once),
                        Toast.LENGTH_SHORT).show();
            back_pressed = System.currentTimeMillis();
            //super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch (id) {
            case R.id.action_start_fragment:
                accountHelper = null;
                unCheck();
                goStartFragment();
                return true;
            case R.id.action_info_ac:
                openAcInfoFragment();
                return true;
            case R.id.action_exit:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void unCheck() {
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.getMenu().getItem(itemIdx).setChecked(false);
    }

    private void openAcInfoFragment() {
        AcInfoFragment fragment = new AcInfoFragment();
        Bundle args = new Bundle();
        String acName = accountHelper.getAcName();
        args.putString(Consts.AC_INFO, acName);
        fragment.setArguments(args);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.main_container, fragment);
        fragmentTransaction.addToBackStack(AcInfoFragment.class.getSimpleName());
        fragmentTransaction.commit();
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.nav_trophy:
                itemIdx = 0;
                getSupportActionBar().setTitle(R.string.add_trophy_title);
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.main_container, new AddTrophyFragment())
                        .commit();
                break;
            case R.id.nav_casualties:
                itemIdx = 1;
                getSupportActionBar().setTitle(R.string.calc_loss);
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.main_container, new LossFragment())
                        .commit();
                break;
            case R.id.nav_previously:
                itemIdx = 2;
                getSupportActionBar().setTitle(R.string.preview_title);
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.main_container, new PreviewFragment())
                        .commit();
                break;
            case R.id.nav_save:
                itemIdx = 3;
                Intent intent = new Intent(this, SaveActivity.class);
                intent.putExtra(Consts.ACC_NAME, accountHelper.getAcName());
                startActivityForResult(intent, Consts.REQUEST_SAVE);
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public AccountHelper getAccountHelper(String name){
        if(accountHelper == null){
            AccountHelper ah = new AccountHelper(this, name);
            accountHelper = ah;
            return accountHelper;
        }
        if(name.equals(accountHelper.getAcName())){
            return accountHelper;
        }
        AccountHelper ah = new AccountHelper(this, name);
        accountHelper = ah;
        return accountHelper;
    }

    public AccountHelper getAccountHelper(){
        if(accountHelper == null) {
            Toast toast = Toast.makeText(this, R.string.load_acc_err, Toast.LENGTH_SHORT);
            toast.show();
            return getAccountHelper(Consts.TEMP_NAME);
        }
        return accountHelper;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == Consts.REQUEST_SAVE) {
            if(resultCode == Activity.RESULT_OK){
                String name = intent.getStringExtra(Consts.SAVE);
                accountHelper.setAcName(name);
                saves.saveAcc(accountHelper.getAcName(), accountHelper.getAccGSON());
                return;
            } else {
                Toast toast = Toast.makeText(this, R.string.save_cancel, Toast.LENGTH_SHORT);
                toast.show();
            }

        } else {
            super.onActivityResult(requestCode, resultCode, intent);
        }
    }

}
