package com.wheelabs.wheecheck.wheecheck;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import com.parse.Parse;
import com.parse.ParseUser;

/**
 * Created by nikola on 10/14/14.
 */
public class ActivityUserHome extends ActionBarActivity implements AdapterView.OnItemClickListener {

    private DrawerLayout drawerLayout;
    private ListView listView;
    private String[] menuItems;
    private ActionBarDrawerToggle drawerListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home);
        // Parse.com
        Parse.initialize(this, "DS51bI5NWK50Juxj66qNrumszEOdEHmzUopU3mRK", "FCvutL8DQR7DfmdOdVuEgiex9VRtcsWwvcX8QU4e");

        //XML Bridge
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        listView = (ListView) findViewById(R.id.drawerList);
        menuItems = getResources().getStringArray(R.array.menu);

        listView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, menuItems));
        listView.setOnItemClickListener(this);



        drawerListener = new ActionBarDrawerToggle(this, drawerLayout, R.drawable.ic_navigation_drawer, R.string.drawer_open, R.string.drawer_close);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        drawerLayout.setDrawerListener(drawerListener);


    }

    // DrawerMenu Settings

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerListener.syncState();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(this, menuItems[position], Toast.LENGTH_SHORT).show();
        selectItem(position);
    }

    public void selectItem(int position) {
        listView.setItemChecked(position, true);
        setTitle(menuItems[position]);
    }

    public void setTitle(String title){
        getSupportActionBar().setTitle(title);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerListener.onConfigurationChanged(newConfig);
    }

    // Action Bar Settings
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action_bar_items, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // Navigation Drawer - LEFT
        if(drawerListener.onOptionsItemSelected(item)){
            return true;
        }

        // Action Bar Menu - RIGHT
        switch (item.getItemId()){
            case R.id.log_out:
                ParseUser.logOut();
                Intent activityMain = new Intent(ActivityUserHome.this, ActivityMain.class);
                startActivity(activityMain);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
    }



    // End of ActivityUserHome.class
}
