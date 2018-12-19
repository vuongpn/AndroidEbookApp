package com.vuong.ebookapp;

import android.app.Fragment;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends ActionBarActivity
        implements NavigationDrawerCallbacks {

    private NavigationDrawerFragment mNavigationDrawerFragment;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawer_activity_main);
        mToolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(mToolbar);



        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getFragmentManager().findFragmentById(R.id.fragment_drawer);

        // Set up the drawer.
        mNavigationDrawerFragment.setup(R.id.fragment_drawer, (DrawerLayout) findViewById(R.id.drawer), mToolbar);
        // populate the navigation drawer
        mNavigationDrawerFragment.setUserData(getResources().getString(R.string.app_name), getResources().getString(R.string.email), BitmapFactory.decodeResource(getResources(), R.drawable.ic_logo_app));
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        Fragment fragment;
        switch (position) {
            case 0: //stats
                fragment = getFragmentManager().findFragmentByTag(Fragment_Books.TAG);
                if (fragment == null) {
                    fragment = new Fragment_Books();
                }
                getFragmentManager().beginTransaction().replace(R.id.container, fragment, Fragment_Books.TAG).commit();
                break;
            case 1:
                fragment = getFragmentManager().findFragmentByTag(Fragment_Favorite.TAG);
                if (fragment == null) {
                    fragment = new Fragment_Favorite();
                }
                getFragmentManager().beginTransaction().replace(R.id.container, fragment, Fragment_Favorite.TAG).commit();
                break;
            case 2:
                fragment = getFragmentManager().findFragmentByTag(Fragment_About.TAG);
                if (fragment == null) {
                    fragment = new Fragment_About();
                }
                getFragmentManager().beginTransaction().replace(R.id.container, fragment, Fragment_About.TAG).commit();
                break;
        }
    }


    @Override
    public void onBackPressed() {
        if (mNavigationDrawerFragment.isDrawerOpen())
            mNavigationDrawerFragment.closeDrawer();
        else
            super.onBackPressed();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.unused, menu);
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();



        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
