package com.wheelabs.wheecheck.wheecheck;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.parse.Parse;
import com.parse.ParseUser;

/**
 * Created by nikola on 10/14/14.
 */
public class ActivityUserHome extends ActionBarActivity implements AdapterView.OnItemClickListener {

    private DrawerLayout drawerLayout;
    private ListView listView;
    private ActionBarDrawerToggle drawerListener;
    private MenuDrawerAdapter DrawerAdapter;
    FragmentManager manager;
    FragmentTransaction transaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home);
        // Parse.com
        Parse.initialize(this, "DS51bI5NWK50Juxj66qNrumszEOdEHmzUopU3mRK", "FCvutL8DQR7DfmdOdVuEgiex9VRtcsWwvcX8QU4e");

        //XML Bridge
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        DrawerAdapter = new MenuDrawerAdapter(this);

        listView = (ListView) findViewById(R.id.drawerList);
        listView.setAdapter(DrawerAdapter);
        listView.setOnItemClickListener(this);

        drawerListener = new ActionBarDrawerToggle(this, drawerLayout, R.drawable.ic_navigation_drawer, R.string.drawer_open, R.string.drawer_close);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        drawerLayout.setDrawerListener(drawerListener);

        // Get FragmentUserHomeContent to main screen
        FragmentUserHomeContent homeFragment = new FragmentUserHomeContent();
        manager = getSupportFragmentManager();
        transaction = manager.beginTransaction();
        transaction.add(R.id.mainContent, homeFragment, "homeFragment");
        transaction.commit();

    }

    // DrawerMenu Settings
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerListener.syncState();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        selectItem(position);
    }

    public void selectItem(int position) {
        Fragment newFragment;
        transaction = manager.beginTransaction();
        switch (position){
            case 0:
                newFragment =  new FragmentUserHomeContent();
                transaction.replace(R.id.mainContent, newFragment);
                transaction.addToBackStack(null);
                transaction.commit();
                break;
            case 1:
                newFragment = new FragmentUserHomeClassroom();
                transaction.replace(R.id.mainContent, newFragment);
                transaction.addToBackStack(null);
                transaction.commit();
                break;
            case 2:
                newFragment = new FragmentUserHomeStatistics();
                transaction.replace(R.id.mainContent, newFragment);
                transaction.addToBackStack(null);
                transaction.commit();
                break;
        }
        listView.setItemChecked(position, true);
        String[] menuItems = getResources().getStringArray(R.array.menu);
        setTitle(menuItems[position]);
        drawerLayout.closeDrawer(listView);
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

// Custom Adapter to set Custom Row -> drawer_menu_custom_row
class MenuDrawerAdapter extends BaseAdapter {

    private Context context;
    String[] menuItems;
    // Order of images items = string-array items
    int[] images = {R.drawable.ic_launcher, R.drawable.ic_launcher, R.drawable.ic_launcher};

    public MenuDrawerAdapter(Context context){
        this.context = context;
        menuItems = context.getResources().getStringArray(R.array.menu);
    }

    @Override
    public int getCount() {
        return menuItems.length;
    }

    @Override
    public Object getItem(int position) {
        return menuItems[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = null;
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.drawer_menu_custom_row, parent, false);
        }else {
            row = convertView;
        }
        TextView titleTexView = (TextView) row.findViewById(R.id.tvTitle);
        ImageView titleImageView = (ImageView) row.findViewById(R.id.ivImage);
        titleTexView.setText(menuItems[position]);
        titleImageView.setImageResource(images[position]);
        return row;
    }
}